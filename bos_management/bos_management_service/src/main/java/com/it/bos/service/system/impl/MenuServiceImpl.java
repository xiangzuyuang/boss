package com.it.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.system.MenuRepository;
import com.it.bos.domain.system.Menu;
import com.it.bos.domain.system.User;
import com.it.bos.service.system.MenuService;

/**  
 * ClassName:MenuActionImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:02:37 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    
    @Override
    public List<Menu> findLevelOne() {
          
        return menuRepository. findByParentMenuIsNull();
    }

    @Override
    public void save(Menu menu) {
        if (menu.getParentMenu()!=null&&menu.getParentMenu().getId()==null) {
            menu.setParentMenu(null);
        }
          
        menuRepository.save(menu);
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findbyUser(User user) {
          
        if ("admin".equals(user.getUsername())) {
            return menuRepository.findAll();
        }
        return menuRepository.findbyUser(user.getId());
    }

   

}
  
