package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.entity.Product;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select c from Category c join Product p on p.category.id = c.id where p.id=?1")
    List<Category> getCategoryByProductId(Integer product_id);

}
