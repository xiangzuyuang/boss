package com.it.bos.service.bose.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.StandardRepository;
import com.it.bos.domain.Standard;
import com.it.bos.service.bose.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:37:42 <br/>       
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard standard) {
   
        standardRepository.save(standard);
    
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
          
        return standardRepository.findAll(pageable);
    }

}
  
