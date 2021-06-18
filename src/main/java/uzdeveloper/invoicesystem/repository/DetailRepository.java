package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Detail;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Integer> {

    @Query(value = "select sum(p.price) from Detail d join Product p on p.id=d.product.id where d.order.id= ?1")
    Double getListOfDetailsByOrderId(Integer orderId);


    @Query(value = "select sum(d.quantity) from  Detail d where d.order.id = ?1")
    Integer getQuantityByOrderId(Integer id);

    @Query(value = "select d.order.id from Detail d ")
    List<Integer> listOfOrderIds();

    @Query(value = "select d.product.id from Detail d  where d.quantity>=8 order by d.product.id")
    List<Integer> listOfProductIds();


}
