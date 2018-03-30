package com.it.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:39:48 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
}
  
