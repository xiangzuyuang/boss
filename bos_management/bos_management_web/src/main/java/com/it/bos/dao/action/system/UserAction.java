package com.it.bos.dao.action.system;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.it.bos.domain.system.User;
import com.it.bos.service.system.UserService;
import com.it.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午4:13:02 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    public UserAction() {

        super(User.class);

    }

    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value = "userAction_login", results = {
            @Result(name = "success", 
            location = "/index.html", type = "redirect"),
            @Result(name = "login", 
            location = "/login.html", type = "redirect")})
    public String login() {
          //获得系统中的验证码
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        System.out.println(checkcode);
        if (StringUtils.isNotEmpty(checkcode)
                &&StringUtils.isNotEmpty(serverCode)
                &&checkcode.equals(serverCode)) {
            
            Subject subject = SecurityUtils.getSubject();
            
            
            AuthenticationToken token = new UsernamePasswordToken(
                    getModel().getUsername(), getModel().getPassword());
            subject.login(token );
            
            //保存登录的用户
         User user  = (User) subject.getPrincipal();
         ServletActionContext.getRequest().getSession().setAttribute("user", user);
            
            return SUCCESS;
        }
        
        return LOGIN;
    }
    
    
    @Action(value = "userAction_logout", results = {
            @Result(name = "success", 
            location = "/index.html", type = "redirect")})
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        
        return SUCCESS;
    }
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    
    @Autowired
    private UserService userService;
    
    @Action(value = "userAction_save", results = {
            @Result(name = "success", 
            location = "/pages/system/userlist.html", type = "redirect")})
    public String save() {
       
        userService.save(getModel(),roleIds);
        return SUCCESS;
    }//
    
    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws IOException {
        
        Pageable pageable = new PageRequest(page - 1, rows);
       Page<User> page  = userService.findAll(pageable);
       
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[]{"roles"});
       
       page2json(page, jsonConfig);
        return NONE;
    }
}
