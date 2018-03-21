package com.it.bos.service.bose.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.CourierRepository;
import com.it.bos.dao.base.FixedAreaRepository;
import com.it.bos.dao.base.TakeTimeRepository;
import com.it.bos.domain.Courier;
import com.it.bos.domain.FixedArea;
import com.it.bos.domain.TakeTime;
import com.it.bos.service.bose.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:21:48 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void save(FixedArea model) {
          fixedAreaRepository.save(model);
    }

    @Override
    public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
          
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        
        courier.setTakeTime(takeTime);
        
        fixedArea.getCouriers().add(courier);
        
    }

    
}
  
