package com.it.bos.service.bose.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.AreaRepository;
import com.it.bos.domain.Area;
import com.it.bos.service.bose.AeraService;

/**  
 * ClassName:AreaSeriveImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午7:00:15 <br/>       
 */
 @Transactional
 @Service
public class AreaSeriveImpl implements AeraService {

     @Autowired
     private AreaRepository areaRepository;
    
    @Override
    public void save(List<Area> list) {

        areaRepository.save(list);

    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }

    @Override
    public List<Area> findByQ(String q) {
          
        q= "%" + q.toUpperCase() + "%";
        return areaRepository.findAll(q);
    }

}
  
