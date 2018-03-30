package com.it.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it.bos.domain.Area;
import com.it.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午3:29:47 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    
    private Order model = new Order();

    @Override
    public Order getModel() {

        return model;
    }

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "orderAction_add", results = {@Result(name = "success",
            location = "/index.html", type = "redirect")})
    public String add() {
        // 获取寄件人的省市区的地址
        if (StringUtils.isNoneEmpty(sendAreaInfo)) {

            String[] split = sendAreaInfo.split("/");

            String province = split[0];
            String city = split[1];
            String district = split[2];

            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);

            model.setSendArea(area);;
        }
        // 获取收件人的省市区的地址
        if (StringUtils.isNoneEmpty(recAreaInfo)) {

            String[] split = recAreaInfo.split("/");

            String province = split[0];
            String city = split[1];
            String district = split[2];

            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);

            model.setRecArea(area);
        }
        // 保存订单到后台服务器
        WebClient
                .create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(model);

        return SUCCESS;
    }
}
