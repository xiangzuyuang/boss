package com.it.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.it.bos.domain.system.Menu;
import com.it.bos.domain.system.User;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:01:51 <br/>       
 */
public interface MenuService {

    List<Menu> findLevelOne();

    void save(Menu menu);

    Page<Menu> findAll(Pageable pageable);

    List<Menu> findbyUser(User user);


}
  
