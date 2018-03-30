package com.it.bos.service.take_delivery;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.it.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午9:25:41 <br/>       
 */
public interface OrderService {
    
    @POST
    @Path("/saveOrder")
    void saveOrder(Order order);
    

}
  
