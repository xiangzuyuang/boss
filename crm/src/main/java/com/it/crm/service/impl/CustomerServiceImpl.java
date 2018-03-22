package com.it.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.crm.dao.CustomerRepository;
import com.it.crm.domain.Customer;
import com.it.crm.service.CustomerService;

/**
 * ClassName:CustomerServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午4:13:15 <br/>
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssociated() {

        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {

        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
          if (StringUtils.isNoneEmpty(fixedAreaId)) {
              customerRepository.unbindCustomerByFixedArea(fixedAreaId);
        }
        if (customerIds != null && fixedAreaId.length() > 0) {
            for (Long customerId : customerIds) {
                customerRepository.bindCustomer2FixedArea(customerId,
                        fixedAreaId);
            }
           
        }
        
    }

    @Override
    public void save(Customer customer) {
          customerRepository.save(customer);
        
    }

    @Override
    public void active(String telephone) {
          customerRepository.active(telephone);
        
    }

    @Override
    public Customer isActived(String telephone) {
          
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
          
        return customerRepository.findByTelephoneAndPassword(telephone, password);
    }

}
