package com.it.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:56:08 <br/>       
 */
public interface PermissionService {

    Page<Permission> findAll(Pageable pageable);

    void save(Permission model);

}
  
