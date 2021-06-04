package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Category;

import java.util.List;

@Service
public interface CategoryService {

    public Response getAllCategories();
    public Response getOneCategory(Integer id);
    public Response addCategory(Category category);
    public Response addCategories(List<Category> category);
    public Response updateCategory(Integer id, Category category);
    public Response deleteCategory(Integer id);


    public  Response getByProductId(Integer product_id);
}
