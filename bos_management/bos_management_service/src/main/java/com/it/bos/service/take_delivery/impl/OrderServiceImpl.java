package com.it.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.bos.dao.base.AreaRepository;
import com.it.bos.dao.base.FixedAreaRepository;
import com.it.bos.dao.take_delivery.OrderRepository;
import com.it.bos.dao.take_delivery.WorkBillRepository;
import com.it.bos.domain.Area;
import com.it.bos.domain.Courier;
import com.it.bos.domain.FixedArea;
import com.it.bos.domain.SubArea;
import com.it.bos.domain.take_delivery.Order;
import com.it.bos.domain.take_delivery.WorkBill;
import com.it.bos.service.take_delivery.OrderService;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午9:26:14 <br/>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private WorkBillRepository workBillRepository;

    @Override
    public void saveOrder(Order order) {

        //将前端传过来的瞬时态地址转变为后台数据存储得到持久态
        Area sendArea = order.getSendArea();
        if (sendArea != null) {
            Area sendAreaDb = areaRepository.findByProvinceAndCityAndDistrict(
                    sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());

            order.setSendArea(sendAreaDb);
        }

        Area recArea = order.getRecArea();
        if (recArea != null) {
            Area recAreaDb = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());

            order.setRecArea(recAreaDb);
        }
        //保存订单
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderTime(new Date());
        orderRepository.save(order);
        
        //获取订单中的详细地址
        String sendAddress = order.getSendAddress();
        System.out.println(sendAddress);
        
        //根据详细地址获取定区ID
        // ---根据发件地址完全匹配
        // 让crm系统根据发件地址查询定区ID
        // 做下面的测试之前,必须在定区中关联一个客户,下单的页面填写的地址,必须和这个客户的地址一致
        
        //自动分单
        if (StringUtils.isNotEmpty(sendAddress)) {
           String fixedAreaId = WebClient
            .create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAdddress")
            .type(MediaType.APPLICATION_JSON)
            .query("address", sendAddress)
            .accept(MediaType.APPLICATION_JSON).get(String.class);
            
            
           if (StringUtils.isNotEmpty(fixedAreaId)) {
             //通过定区ID(fixedAreaId)获取定区
               FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
               
               if (fixedArea!=null) {
                   //根据定区查找对应的快递员
                    Set<Courier> couriers = fixedArea.getCouriers();
                    if(couriers!=null){
                        Iterator<Courier> iterator = couriers.iterator();
                        Courier courier = iterator.next();
                        order.setCourier(courier);
                        
                        //生成工单
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setSmsNumber("111");
                        workBill.setType("新");
                        
                        //将工单保存到后台数据库
                        workBillRepository.save(workBill);
                        //设置订单的类型
                        order.setOrderType("自动分单");
                        return;
                    }
                    
            }
        }else {
            //获取寄件人省市区信息
            Area sendArea2 = order.getSendArea();
               if (sendArea2!=null) {
                   //获得关联的分区
                  Set<SubArea> subareas = sendArea2.getSubareas();
                   if (sendArea2!=null) {
                     for (SubArea subArea : subareas) {
                         //获取关键字
                         String keyWords = subArea.getKeyWords();
                         //获取辅助关键字
                         String assistKeyWords = subArea.getAssistKeyWords();
                         //判断详细地址在是否包含关键字或辅助关键字
                         if (sendAddress.contains(keyWords)||sendAddress.contains(assistKeyWords)) {
                            //根据分区找到定区
                             FixedArea fixedArea2 = subArea.getFixedArea();
                             //判断定区是否为空 
                             if (fixedArea2!=null) {
                                //根据定区查找快递员
                                 Set<Courier> couriers = fixedArea2.getCouriers();
                                  Iterator<Courier> iterator = couriers.iterator();
                                  Courier courier = iterator.next();
                                  //指定快递员
                                  order.setCourier(courier);
                                  
                                  //生成工单
                                  WorkBill workBill = new WorkBill();
                                  workBill.setAttachbilltimes(0);
                                  workBill.setBuildtime(new Date());
                                  workBill.setCourier(courier);
                                  workBill.setOrder(order);
                                  workBill.setPickstate("新单");
                                  workBill.setRemark(order.getRemark());
                                  workBill.setSmsNumber("111");
                                  workBill.setType("新");
                                  
                                  workBillRepository.save(workBill);
                                  
                                  order.setOrderType("自动分单");
                                  
                                  return;
                                  
                            }
                        }
                         
                    }
                }
            }
        }
           
        }
        
        order.setOrderType("人工分单");
    }

}
