package com.it.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.bos.domain.FixedArea;
import com.it.bos.domain.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午5:16:18 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>{

    List<SubArea> findByFixedAreaIsNull();

    List<SubArea> findByFixedArea(FixedArea fixedArea);

}
  
