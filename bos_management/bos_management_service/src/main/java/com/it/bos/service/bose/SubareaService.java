package com.it.bos.service.bose;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午5:12:54 <br/>       
 */
public interface SubareaService {

    void save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

    List<SubArea> findUnAssociatedSubAreas();

    List<SubArea> findAssociatedSubAreas(Long fixedAreaId);

}
  
