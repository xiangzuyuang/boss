package com.it.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.system.MenuRepository;
import com.it.bos.dao.system.PermissionRepository;
import com.it.bos.dao.system.RoleRepository;
import com.it.bos.domain.system.Menu;
import com.it.bos.domain.system.Permission;
import com.it.bos.domain.system.Role;
import com.it.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:20:59 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Override
    public Page<Role> findAll(Pageable pageable) {
          
        return roleRepository.findAll(pageable);
    }


    @Override
    public void save(Role role, String menuIds, Long[] permissionIds) {
          
        roleRepository.save(role);
        
        if (StringUtils.isNoneEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = menuRepository.findOne(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        
        if (permissionIds!=null&&permissionIds.length>0) {
            for (Long permissionId : permissionIds) {
                Permission permission = permissionRepository.findOne(permissionId);
                role.getPermissions().add(permission);
            }
        }
    }


   

}
  
