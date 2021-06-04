package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Detail;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail,Integer> {

    @Query(value = "select d from Detail d where d.order.id= ?1")
    List<Detail> getListOfDetailsByOrderId(Integer orderId);



}
