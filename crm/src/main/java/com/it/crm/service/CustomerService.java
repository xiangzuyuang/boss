package com.it.crm.service;

import java.util.List;

import javax.print.attribute.standard.MediaTray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.it.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:12:39 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {

    @GET
    @Path("/findAll")
    List<Customer> findAll();
    
    @GET
    @Path("/findCustomersUnAssociated")
    List<Customer> findCustomersUnAssociated();
    
    @GET
    @Path("/findCustomersAssociated2FixedArea")
    List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId,
            @QueryParam("customerIds")Long[] customerIds);
    
    @POST
    @Path("/save")
    void save(Customer customer);
    
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone")String telephone);
    
    @GET
    @Path("/isActived")
    Customer isActived(@QueryParam("telephone")String telephone);
    
    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone")String telephone,@QueryParam("password")String password);
}
  
