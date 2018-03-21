package com.it.bos.web.action.bose;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.it.bos.domain.Courier;
import com.it.bos.domain.Standard;
import com.it.bos.service.bose.CourierService;
import com.it.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午5:10:50 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {

    public CourierAction() {
          
        super(Courier.class);  
        // TODO Auto-generated constructor stub  
        
    }

    private Courier model = new Courier();
    @Autowired
    private CourierService courierService;

    
    @Action(value = "courierAction_save", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(model);
        return SUCCESS;
    }

   
    @Action(value = "courierAction_pageQuery", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String pageQuery() throws IOException {

        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> arg1,
                    CriteriaBuilder cb) {

                
                String courierNum = model.getCourierNum();
                String company = model.getCompany();
                String type = model.getType();
                Standard standard = model.getStandard();

                List<Predicate> list =new ArrayList<>();
                if (StringUtils.isNoneEmpty(courierNum)) {
                 Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                 list.add(p1);
                }
                if (StringUtils.isNoneEmpty(company)) {
                  Predicate p2 = cb.like(root.get("company").as(String.class),"%"+ company+"%");
                  list.add(p2);
                }
                if (StringUtils.isNoneEmpty(type)) {
                Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                list.add(p3);
                }
               if (standard!=null) {
                String name = standard.getName();
                  if (StringUtils.isNoneEmpty(name)) {
                      Join<Object, Object> join = root.join("standard");
                      Predicate p4 = cb.equal(join.get("standard").as(String.class), name);
                      list.add(p4);
                }
            }
                if (list.size()==0) {
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                Predicate predicate = cb.and(arr);
                return predicate;
            }
        };

       

        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Courier> page = courierService.findAll(specification, pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});

        page2json(page, jsonConfig);


        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String batchDel() {
        courierService.batchDel(ids);
        return SUCCESS;
    }
    
   
    @Action(value = "courierAction_listajax")
    public String listajax() throws IOException {
        List<Courier> list =courierService.findAvaible();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        list2json(list, jsonConfig);
        return NONE;
    }
}
