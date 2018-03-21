package com.it.bos.web.action.bose;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it.bos.domain.TakeTime;
import com.it.bos.service.bose.TakeTimeService;
import com.it.bos.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:45:31 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
          
        super(TakeTime.class);  
        
    }
    @Autowired
    private TakeTimeService takeTimeService;
    
    @Action(value="takeTimeAction_listaj")
    public String listaj() throws IOException{
        
       List<TakeTime> list =takeTimeService.findAll();
        list2json(list, null);
        return NONE;
    }
    

}
  
