package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


//    @Query(value = "select c.id,c.name,o.date  from  Customer c left join Order o on o.customer.id = c.id where  " +
//            " o.customer.id is not null  and o.customer.id > 1")


    @Query(value = "select c.country from Customer c group by c.country")
    List<String> getCountryCodes();


}
