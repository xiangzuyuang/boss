package com.it.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:24:31 <br/>       
 */
public interface UserService {

    void save(User model, Long[] roleIds);

    Page<User> findAll(Pageable pageable);

}
  
