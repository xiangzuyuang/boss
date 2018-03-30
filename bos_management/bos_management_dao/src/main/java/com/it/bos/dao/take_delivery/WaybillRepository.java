package com.it.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WaybillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:51:44 <br/>       
 */
@Service
@Transactional
public interface WaybillRepository extends JpaRepository<WayBill, Long>{

}
  
