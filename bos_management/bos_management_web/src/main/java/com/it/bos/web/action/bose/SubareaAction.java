package com.it.bos.web.action.bose;

import java.io.IOException;

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

import com.it.bos.domain.SubArea;
import com.it.bos.service.bose.SubareaService;
import com.it.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午5:05:33 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubareaAction extends CommonAction<SubArea> {

    public SubareaAction() {
          
        super(SubArea.class);  
        
    }
    @Autowired
    private SubareaService subAreaService;
    
    
    @Action(value="subAreaAction_pageQuery",results={@Result(name="success",
    location="/pages/base/sub_area.html",type="redirect")})
    public String pageQuery() throws IOException{
        
       Pageable pageable =new PageRequest(page - 1, rows);
       Page<SubArea>page = subAreaService.findAll(pageable);
      
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[]{"area","fixedArea"});
       page2json(page, jsonConfig);
        return NONE;
    }
    
    @Action(value="subAreaAction_save",results={@Result(name="success",
            location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
    }

}
  
