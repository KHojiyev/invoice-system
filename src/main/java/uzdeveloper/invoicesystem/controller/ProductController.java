package uzdeveloper.invoicesystem.controller;


import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.ProductDTO;
import uzdeveloper.invoicesystem.service.implement.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @PostMapping("/uploadToServer")
    public HttpEntity<Response> uploadToServer(@RequestParam("files")MultipartFile[] files){
       return productServiceImpl.uploadToServer(files);
    }

    @GetMapping("/list")
    public Response getAllProducts() {
        return productServiceImpl.getAllProducts();
    }

    @GetMapping("/details")
    public Response getOneProduct(@RequestParam Integer id) {
        return productServiceImpl.getOneProduct(id);
    }

    @PostMapping("/one")
    public Response addProduct(@RequestBody ProductDTO productDTO) {
        return productServiceImpl.addProduct(productDTO);
    }

    @PostMapping
    public Response addProducts(@RequestBody List<ProductDTO> productsDTO) {
        return productServiceImpl.addProducts(productsDTO);
    }

    @PutMapping("/{id}")
    public Response updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productServiceImpl.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Integer id) {
        return productServiceImpl.deleteProduct(id);
    }

    @GetMapping("/high_demand_products")
    public Response high_demand_products() {
        return productServiceImpl.high_demand_products();
    }

    @GetMapping("/number_of_products_in_year")
    public Response number_of_products_in_ye() {
        return productServiceImpl.number_of_products_in_ye();
    }

    @GetMapping("/bulk_products")
    public Response bulk_products() {
        return productServiceImpl.bulk_products();
    }

    ;

}
