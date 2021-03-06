package com.it.bos.service.bose;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.domain.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午9:20:34 <br/>       
 */

public interface FixedAreaService {

    Page<FixedArea> findAll(Pageable pageable);

    void save(FixedArea model);

    void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId);

    void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds);


}
  
