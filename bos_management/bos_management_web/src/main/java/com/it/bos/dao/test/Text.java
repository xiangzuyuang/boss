package com.it.bos.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.it.bos.dao.base.StandardRepository;
import com.it.bos.domain.Standard;

/**  
 * ClassName:Test <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:51:28 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Text {

    @Autowired
    private StandardRepository standardRepository;
    
    @Test
    public void test01(){
        Standard standard = new Standard();
        standard.setName("张三");
        standard.setMaxWeight(300);
        standardRepository.save(standard);
    }
    
    @Test
    public void test02(){
        
        List<Standard> one= standardRepository.findAll();
          for (Standard standard : one) {
              System.out.println(standard);
        }
    }  
    @Test
    public void test03(){
       Standard one = standardRepository.findOne(3L);
       System.out.println(one);
    }
    @Test
    public void test04(){
        Standard standard =new Standard();
        standard.setId(2L);
        standard.setName("奥巴马");
        standard.setMaxWeight(530);
        standardRepository.save(standard);
       
    }
}
  