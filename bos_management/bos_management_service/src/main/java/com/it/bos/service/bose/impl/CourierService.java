package com.it.bos.service.bose.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.it.bos.domain.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:16:35 <br/>       
 */
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findAll(Pageable pageable);

    void batchDel(String ids);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

}
  
