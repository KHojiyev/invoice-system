package uzdeveloper.invoicesystem.repository;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Customer;
import uzdeveloper.invoicesystem.entity.Order;
import uzdeveloper.invoicesystem.response.OrdersWithoutInvoices;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Query(value = "select o from Order o left join Detail d on d.order.id= o.id where " +
            "            o.date <= '2016-09-06T00:00:00' and d.order.id is null ")
    List<Order> orders_without_details();

    Order findByCustomerAndDate(Customer customer, LocalDate date);


    @Query(value = "select count(o) from Order o join Detail d on d.order = o.id " +
            "join Product pr on pr.id = d.product.id " +
            "where pr.id = ?1")
    Integer getOrderCountByProductId(Integer productId);


    @Query(value = "select count(o) from Order o join Customer c on c.id=o.customer.id " +
            "where o.date between '2016-01-01T00:00:00' and '2016-12-31T23:59:59' and c.country=?1")
    Integer getCountOrdersByCountry(String countryCode);


    @Query(value = " select count(o) from Order o join Detail d on d.order.id = o.id " +
            "join Product pr on pr.id = d.product.id where  pr.id=?1")
    Integer getOrderCountByProductID(Integer productId);


    @Query(value = " select i.order.id from Order o join Invoice  i on i.order.id = o.id  group by i.order.id")
    List<Integer> getOrdersByOrderId();


    @Query(value = "select o from Order o join Customer c on c.id = o.customer.id where c.id = ?1 order by o.date desc ")
    List<Order> getLastOrderByCustomerId(Integer customerId);


    @Query(value = "select o.customer.id from Order o group by o.customer.id order by o.customer.id")
    List<Integer> getAllCustomerIds();


}
