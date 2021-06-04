package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.service.implement.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping("/list")
    public Response getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }

    @GetMapping("/{id}")
    public Response getOneCategory(@PathVariable Integer id) {
return categoryServiceImpl.getOneCategory(id);

    }

    @PostMapping("/one")
    public Response addCategory(@RequestBody Category category){
        return categoryServiceImpl.addCategory(category);
    }

    @PostMapping
    public Response addCategories(@RequestBody List<Category> category){
        return categoryServiceImpl.addCategories(category);
    }

    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable Integer id,@RequestBody Category category){
        return categoryServiceImpl.updateCategory(id,category);
    }

    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Integer id){
        return categoryServiceImpl.deleteCategory(id);
    }

    @GetMapping
    public Response getByProductId(@RequestParam Integer product_id){
        return categoryServiceImpl.getByProductId(product_id);
    }





}
