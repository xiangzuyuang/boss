package com.it.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.it.crm.domain.Customer;


/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:15:16 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    List<Customer>findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId=null  where fixedAreaId = ?")
    @Modifying
    void unbindCustomerByFixedArea(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void bindCustomer2FixedArea(Long customerId, String fixedAreaId);
    
    @Query("update Customer set type = 1 where telephone = ?")
    @Modifying
    void active(String telephone);
    
    Customer findByTelephone(String telephone);
    
    Customer findByTelephoneAndPassword(String telephone,String password);
}
  
