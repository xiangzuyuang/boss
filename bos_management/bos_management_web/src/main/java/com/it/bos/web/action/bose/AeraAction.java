package com.it.bos.web.action.bose;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import org.springframework.stereotype.Controller;

import com.iit.bos.service.bose.AeraService;
import com.it.bos.domain.Area;
import com.it.bos.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AeraAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:17:10 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AeraAction extends ActionSupport implements ModelDriven<Area>{

    private Area model =new Area();
    @Override
    public Area getModel() {
          
        return model;
    }
    @Autowired
    private AeraService aeraService;
    
    private File file;
    
    public void setFile(File file) {
        this.file = file;
    }
    
    @Action(value="aeraAction_importXLS",results={@Result(name="success",
            location="/pages/dase/area.html",type="redirect")})
    public String importXLS(){
        
        try {
            //获得上传文件对象
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            //获取第一页工作溥
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Area>list = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum()==0) {
                    continue;
                }
                //获取数据
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                //去掉最后的省,市,区的字样
                province=province.substring(0, province.length()-1);
                city=city.substring(0, city.length()-1);
                district=district.substring(0, district.length()-1);
                
                //获得城市编码和简码
                String citycode = PinYin4jUtils.hanziToPinyin(city,"");
                String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                
                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);
                
                list.add(area);
                
            }
            aeraService.save(list);
            
        } catch (IOException e) {
            e.printStackTrace();  
            
        }
        return SUCCESS;
    }

    private int page;
    private int rows;
    
    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value="areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable =new PageRequest(page - 1, rows);
        Page<Area> page=  aeraService.findAll(pageable);
        long tatol = page.getTotalElements();
        List<Area> list = page.getContent();
        
        Map<String, Object>map = new HashMap<>();
        map.put("total", tatol);
        map.put("rows", list);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
                
        
        String json = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("applicaton/json;charset=UTF-8");
        
        response.getWriter().write(json);;
        
        return NONE;
    }
}
  
