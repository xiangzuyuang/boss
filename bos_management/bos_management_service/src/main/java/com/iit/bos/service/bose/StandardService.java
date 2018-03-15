package com.iit.bos.service.bose;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.domain.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:35:54 <br/>       
 */

public interface StandardService {

    void save(Standard standard);

    Page<Standard> findAll(Pageable pageable);

}
  
