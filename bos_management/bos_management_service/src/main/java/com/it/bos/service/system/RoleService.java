package com.it.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:20:44 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void save(Role role, String menuIds, Long[] permissionIds);


}
  
