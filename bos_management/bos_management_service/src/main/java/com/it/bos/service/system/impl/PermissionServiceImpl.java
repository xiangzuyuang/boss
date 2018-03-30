package com.it.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.system.PermissionRepository;
import com.it.bos.domain.system.Permission;
import com.it.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:56:34 <br/>       
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
        return permissionRepository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
          
        permissionRepository.save(model);        
    }
}
  
