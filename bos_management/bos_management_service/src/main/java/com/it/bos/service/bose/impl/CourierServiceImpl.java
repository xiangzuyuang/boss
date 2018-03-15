package com.it.bos.service.bose.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.CourierRepository;
import com.it.bos.domain.Courier;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:20:41 <br/>       
 */
@Transactional
@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;
    
    @Override
    public void save(Courier courier) {

        courierRepository.save(courier);

    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
          
        return courierRepository.findAll(pageable);
    }

    @Override
    public void batchDel(String ids) {
          
          if (StringUtils.isNotEmpty(ids)) {
              String[] arr = ids.split(",");
              for (String id : arr) {
                courierRepository.updateDelTagById(Long.parseLong(id));
            }
        }
        
    }

}
  