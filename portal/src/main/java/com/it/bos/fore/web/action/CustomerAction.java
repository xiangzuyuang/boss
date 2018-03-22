package com.it.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.it.bos.utils.MailUtils;
import com.it.bos.utils.SmsUtils;
import com.it.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:25:48 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer model=new Customer();
    @Override
    public Customer getModel() {
         
        return model;
    }
    @Autowired 
    private RedisTemplate<String, String>redisTemplate;
    
    
    
    //发送短信获取验证码
    @Action(value="customerAction_sendSMS")
    public String sendSMS(){
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
        
        try {
            SmsUtils.sendSms(model.getTelephone(), code);
        } catch (ClientException e) {
            e.printStackTrace();  
        }
        return NONE;
    }
    private String checkcode;
    
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //注册客户
    @Action(value="customerAction_regist",
            results={
                    @Result(name="success",location="/signup-success.html",
                            type="redirect"),
                    @Result(name="error",location="/signup-fail.html",
                    type="redirect")})
    public String regist() {
      String code= (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
        
      if (StringUtils.isNotEmpty(checkcode)&&checkcode.equals(code)) {
        WebClient.create("http://localhost:8180/crm/webService/customerService/save")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).post(model);
        //获取激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        //存储激活码
        redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1,
                TimeUnit.DAYS);
        
        String emailBody ="感谢您注册本网站的帐号，请在24小时之内点击<a href='http://localhost:8280/portal/customerAction_active.action?activeCode="
                + activeCode + "&telephone=" + model.getTelephone()
                + "'>本链接</a>激活您的帐号";
        //发送激活邮件
        MailUtils.sendMail("ls@store.com", "激活信息", emailBody );
        
        return SUCCESS;
    }
        return ERROR;
    }
    //获取地址中传过来的激活码
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    
    @Action(value="customerAction_active",results=
        { @Result(name = "success", location = "/login.html",
            type = "redirect"),
          @Result(name = "error", location = "/signup-fail.html",
            type = "redirect")})
    public String active(){
        //获取存在redis中的激活码
        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
        //对比两个激活码
        if (StringUtils.isNotEmpty(serverCode)
                &&StringUtils.isNotEmpty(activeCode)
                &&serverCode.equals(activeCode)) {
            WebClient.create("http://localhost:8180/crm/webService/customerService/active")
            .type(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .accept(MediaType.APPLICATION_JSON).put(null);
            
            return SUCCESS;
        }
        
        return NONE;
    }
    
    @Action(value="customerAction_login",results=
        { @Result(name = "success", location = "/index.html",
            type = "redirect"),
          @Result(name = "error", location = "/login.html",
            type = "redirect"),
          @Result(name = "unactived", location = "/login.html",
            type = "redirect")})
    public String login(){
        
     String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        
        if (StringUtils.isNotEmpty(serverCode)
                &&StringUtils.isNotEmpty(checkcode)
                &&serverCode.equals(checkcode)) {
            
            Customer customer= WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
            .type(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .accept(MediaType.APPLICATION_JSON).get(Customer.class);
            
            if (customer!=null&&customer.getType()!=null) {
                if (customer.getType()==1) {
                    
                    Customer c= WebClient.create("http://localhost:8180/crm/webService/customerService/login")
                            .type(MediaType.APPLICATION_JSON)
                            .query("telephone", model.getTelephone())
                            .query("password", model.getPassword())
                            .accept(MediaType.APPLICATION_JSON).get(Customer.class);
                    if (c!=null) {
                        ServletActionContext.getRequest().getSession().setAttribute("user", c);
                        return SUCCESS;
                    }else{
                        return ERROR;
                    }
                    
                }else{
                   
                    return "unactived";
                }
            }else {
                System.out.println("你还没有激活账户");
                return "unactived";
            }
        }
        
        return ERROR;
    }
}
  
