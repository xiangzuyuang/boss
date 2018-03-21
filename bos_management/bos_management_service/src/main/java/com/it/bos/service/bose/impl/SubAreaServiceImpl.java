package com.it.bos.service.bose.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.SubAreaRepository;
import com.it.bos.domain.SubArea;
import com.it.bos.service.bose.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午5:13:49 <br/>       
 */
@Transactional
@Service
public class SubAreaServiceImpl implements SubareaService {

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Override
    public void save(SubArea model) {
          
        subAreaRepository.save(model);
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
        return subAreaRepository.findAll(pageable);
    }
    
   

}
  
