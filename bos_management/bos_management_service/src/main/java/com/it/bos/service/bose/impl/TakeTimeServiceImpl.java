package com.it.bos.service.bose.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.TakeTimeRepository;
import com.it.bos.domain.TakeTime;
import com.it.bos.service.bose.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:50:31 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired 
    private TakeTimeRepository takeTimeRepository;
    
    @Override
    public List<TakeTime> findAll() {
        
         return takeTimeRepository.findAll();
        
    }

}
  
