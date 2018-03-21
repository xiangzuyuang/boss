package com.it.bos.web.action.bose;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.it.bos.domain.FixedArea;
import com.it.bos.service.bose.FixedAreaService;
import com.it.bos.web.action.CommonAction;
import com.it.crm.domain.Customer;

import net.sf.json.JsonConfig;

/**  
 * ClassName:BixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:04:22 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    public FixedAreaAction() {
          
        super(FixedArea.class);  
    }
   // fixedAreaAction_pageQuery
    @Autowired
    private FixedAreaService fixedAreaService;
    
    @Action(value="fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException{
        
        
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        
        page2json(page, jsonConfig);
        return NONE;
    }
   
    @Action(value="fixedAreaAction_save",results={@Result(name="success",
            location="/pages/base/fixed_area.html",type="redirect")})
    public String save() {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
   
    
    @Action(value="fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {
        List<Customer> list=(List<Customer>) WebClient
         .create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        
        list2json(list, null);
        return NONE;
    }
    
    //
    @Action(value="fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException {
        
        List<Customer> list=(List<Customer>)WebClient
        .create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
        .query("fixedAreaId", getModel().getId())
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
        
        list2json(list, null);
        return NONE;
    }
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",results={@Result(name="success",
            location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea() {
        
        WebClient
                .create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
                .query("fixedAreaId", getModel().getId())
                .query("customerIds", customerIds)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).put(null);
                
        
        return SUCCESS;
    }
    //private Long courierId;
   // private Long takeTimeId;
    private Long courierId;
    private Long takeTimeId;
    
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",
            location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea() {
        
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        
        return SUCCESS;
    }
        
    
}
  
