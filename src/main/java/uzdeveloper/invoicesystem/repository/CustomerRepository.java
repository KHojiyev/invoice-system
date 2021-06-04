package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    @Query(value = "select c from Customer c  left join Order o on o.customer.id = c.id  where o.date  between  '2016-01-01T00:00:00' and '2016-12-31T00:00:00'" +
            "and  o.customer.id is null ")
    List<Customer>   customers_without_orders();


//    @Query(value = "select c.id,c.name,o.date  from  Customer c left join Order o on o.customer.id = c.id where  " +
//            " o.customer.id is not null  and o.customer.id > 1")



    @Query(value = "select c.country from Customer c group by c.country")
    List<String> getCountryCodes();


}
