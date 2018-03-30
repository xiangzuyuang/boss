package com.it.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.take_delivery.WaybillRepository;
import com.it.bos.dao.take_delivery.WorkBillRepository;
import com.it.bos.domain.take_delivery.WayBill;
import com.it.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:53:22 <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WaybillRepository waybillRepository;

    @Override
    public void save(WayBill model) {
          
        waybillRepository.save(model);
        
    }
}
  
