package com.iit.bos.service.bose;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.Area;

/**  
 * ClassName:AeraService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:32:48 <br/>       
 */
public interface AeraService {

    void save(List<Area> list);

    Page<Area> findAll(Pageable pageable);

}
  
