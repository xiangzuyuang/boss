package com.it.bos.dao.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.it.bos.dao.base.StandardRepository;
import com.it.bos.domain.Standard;
import com.it.bos.domain.take_delivery.Order;
import com.it.crm.domain.Customer;

/**
 * ClassName:Test <br/>
 * Function: <br/>
 * Date: 2018年3月12日 下午8:51:28 <br/>
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration("classpath:applicationContext.xml")
public class Text {

    @Autowired
    private StandardRepository standardRepository;

    // @Test
    public void test01() {
        Standard standard = new Standard();
        standard.setName("张三");
        standard.setMaxWeight(300);
        standardRepository.save(standard);
    }

    // @Test
    public void test02() {

        List<Standard> one = standardRepository.findAll();
        for (Standard standard : one) {
            System.out.println(standard);
        }
    }

    // @Test
    public void test03() {
        Standard one = standardRepository.findOne(3L);
        System.out.println(one);
    }

    // @Test
    public void test04() {
        Standard standard = new Standard();
        standard.setId(2L);
        standard.setName("奥巴马");
        standard.setMaxWeight(530);
        standardRepository.save(standard);

    }

    // @Test
    public void test05() {
        standardRepository.delete(2L);
    }

    // @Test
    public void fun06() {
        Collection<? extends Customer> collection =
                WebClient.create("http://localhost:8180/crm/webService/customerService/findAll")
                        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .getCollection(Customer.class);
        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }

    // @Test
    public void fun07() {

        WebClient.create("http://localhost:8180/crm/webService/customerService/findAll")
                .getCollection(Customer.class);
    }

    // @Test
    public void fun08() {
        List<String> l = new ArrayList();
        l.add("aa");
        l.add("bb");
        l.add("cc");
        /*
         * for (Iterator iter = l.iterator(); iter.hasNext();) { String str = (String)iter.next();
         * System.out.println(str); }
         */
        // 迭代器用于while循环
        Iterator iter = l.iterator();
        while (iter.hasNext()) {
            String str = (String) iter.next();
            System.out.println(str);
        }

    }

}
