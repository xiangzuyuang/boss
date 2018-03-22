package com.it.bos.service.bose.impl;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.CourierRepository;
import com.it.bos.dao.base.FixedAreaRepository;
import com.it.bos.dao.base.SubAreaRepository;
import com.it.bos.dao.base.TakeTimeRepository;
import com.it.bos.domain.Courier;
import com.it.bos.domain.FixedArea;
import com.it.bos.domain.SubArea;
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
    
    @Autowired
    private SubAreaRepository subAreaRepository;

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

    @Override
    public void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds) {
          //找到定区对象
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        //用定区对象找到所有分区
        Set<SubArea> subareas = fixedArea.getSubareas();
        //在将所有分区进行解绑
        for (SubArea subArea : subareas) {
            subArea.setFixedArea(null);
        }
        //再绑定
        for (Long subAreaId : subAreaIds) {
            SubArea subArea = subAreaRepository.findOne(subAreaId);
            subArea.setFixedArea(fixedArea);;
        }
        
    }

    
}
  
