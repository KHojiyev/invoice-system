package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.repository.CategoryRepository;
import uzdeveloper.invoicesystem.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Response getAllCategories() {
    if (categoryRepository.findAll().isEmpty())
        return new Response("FAILED","Table is empty");
    return new Response("SUCCESS",categoryRepository.findAll());
    }

    @Override
    public Response getOneCategory(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            return new Response("FAILED","Such category id was not found");
     return new Response("SUCCESS",categoryOptional.get());
    }

    @Override
    public Response addCategory(Category category) {
    Category category1 = new Category();
    category1.setName(category.getName());
    categoryRepository.save(category1);
    return new Response("SUCCESS","Category was added");
    }

    @Override
    public Response addCategories(List<Category> category) {
        for (Category category1 : category) {
            Category category2 = new Category();
            category2.setName(category1.getName());
            categoryRepository.save(category2);
        }
        return new Response("SUCCESS","Categories were added");
    }

    @Override
    public Response updateCategory(Integer id, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            return new Response("FAILED","Such category id was not found");
        categoryOptional.get().setName(category.getName());
        categoryRepository.save(categoryOptional.get());
        return new Response("SUCCESS","Category was updated");

    }

    @Override
    public Response deleteCategory(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            return new Response("FAILED","Such category id was not found");
        categoryRepository.deleteById(id);
        return new Response("SUCCESS","category was deleted");
    }

    @Override
    public Response getByProductId(Integer product_id) {
        return new Response("SUCCESS",categoryRepository.getCategoryByProductId(product_id));
    }


}
