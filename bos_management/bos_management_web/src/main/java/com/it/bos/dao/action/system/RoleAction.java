package com.it.bos.dao.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.it.bos.domain.system.Role;
import com.it.bos.service.system.RoleService;
import com.it.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:RoleAction <br/>
 * Function: <br/>
 * Date: 2018年3月29日 下午4:21:25 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {

        super(Role.class);

    }

    @Autowired
    private RoleService roleService;

    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Role> page = roleService.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});

        page2json(page, jsonConfig);

        return NONE;
    }

    private String menuIds;
    private Long[] permissionIds;
    
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
   
    @Action(value = "roleAction_save", results = {
            @Result(name = "success", location = "/pages/system/role.html", type = "redirect")})
    public String save() {

        roleService.save(getModel(),menuIds,permissionIds);
        return SUCCESS;
    }
    
    @Action(value = "roleAction_findAll")
    public String findAll() throws IOException {
        
        Page<Role> page = roleService.findAll(null);
        List<Role> list = page.getContent();
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});
        
        list2json(list, jsonConfig);
        
        return NONE;
    }

}
