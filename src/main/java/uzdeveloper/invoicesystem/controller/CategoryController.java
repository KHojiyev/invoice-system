package uzdeveloper.invoicesystem.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.service.implement.CategoryServiceImpl;

import javax.management.relation.Role;
import java.util.List;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/api/category")
public class CategoryController {

    final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping("/list")
    public HttpEntity<?> getAllCategories() {
        Response allCategories = categoryServiceImpl.getAllCategories();
        return ResponseEntity.status(allCategories.getStatus().equals("SUCCESS") ? HttpStatus.OK : HttpStatus.BAD_GATEWAY).body(allCategories);
    }

    @PreAuthorize(value = "hasRole('USER')")
    @GetMapping("/{id}")
    public Response getOneCategory(@PathVariable Integer id) {
        return categoryServiceImpl.getOneCategory(id);

    }

    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping("/one")
    public Response addCategory(@RequestBody Category category) {
        return categoryServiceImpl.addCategory(category);
    }

    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping
    public Response addCategories(@RequestBody List<Category> category) {
        return categoryServiceImpl.addCategories(category);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Response updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        return categoryServiceImpl.updateCategory(id, category);
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Integer id) {
        return categoryServiceImpl.deleteCategory(id);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public Response getByProductId(@RequestParam Integer product_id) {
        return categoryServiceImpl.getByProductId(product_id);
    }


}
