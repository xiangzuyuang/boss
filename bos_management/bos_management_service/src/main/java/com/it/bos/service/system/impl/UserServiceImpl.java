package com.it.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.system.RoleRepository;
import com.it.bos.dao.system.UserRepository;
import com.it.bos.domain.system.Role;
import com.it.bos.domain.system.User;
import com.it.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:24:49 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User model, Long[] roleIds) {
          
        userRepository.save(model);
        
        if (roleIds!=null&&roleIds.length>0) {
            for (Long roleId : roleIds) {
                Role role = roleRepository.findOne(roleId);
                model.getRoles().add(role);
            }
        }
        
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
          
        return userRepository.findAll(pageable);
    }
}
  
