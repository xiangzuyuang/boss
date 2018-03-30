package com.it.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.it.bos.domain.system.Role;

/**  
 * ClassName:RoleRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:19:48 <br/>       
 */
public interface RoleRepository extends JpaRepository<Role, Long>{

    @Query("select r from Role r inner join r.users u where u.id=?")
    List<Role> findbyUid(Long uid);

}
  
