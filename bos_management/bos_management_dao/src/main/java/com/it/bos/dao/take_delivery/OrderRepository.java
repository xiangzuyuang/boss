package com.it.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午9:50:56 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>{

}
  
