package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.BulkProducts;
import uzdeveloper.invoicesystem.response.OrdersByCountry;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.response.HighDemandProducts;
import uzdeveloper.invoicesystem.dto.ProductDTO;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.entity.Product;
import uzdeveloper.invoicesystem.repository.CategoryRepository;
import uzdeveloper.invoicesystem.repository.CustomerRepository;
import uzdeveloper.invoicesystem.repository.OrderRepository;
import uzdeveloper.invoicesystem.repository.ProductRepository;
import uzdeveloper.invoicesystem.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Response getAllProducts() {
        if (productRepository.findAll().isEmpty())
            return new Response("FAILED", "Table is empty");
        return new Response("SUCCESS", productRepository.findAll());
    }

    @Override
    public Response getOneProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            return new Response("FAILED", "Product id was not found");
        return new Response("SUCCESS", productOptional.get());
    }

    @Override
    public Response addProduct(ProductDTO productDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (categoryOptional.isEmpty())
            return new Response("FAILED", "category id was not found");
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryOptional.get());
        product.setPrice(productDTO.getPrice());
        product.setPhoto(productDTO.getPhoto());

        productRepository.save(product);
        return new Response("SUCCESS", "product was added");
    }

    @Override
    public Response addProducts(List<ProductDTO> productsDTO) {
        for (ProductDTO productDTO : productsDTO) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
            if (categoryOptional.isEmpty())
                return new Response("FAILED", "category id was not found");
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setCategory(categoryOptional.get());
            product.setPrice(productDTO.getPrice());
            product.setPhoto(productDTO.getPhoto());

            productRepository.save(product);
        }
        return new Response("SUCCESS", "product was added");
    }

    @Override
    public Response updateProduct(Integer id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            return new Response("FAILED", "Product id was not found");

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (categoryOptional.isEmpty())
            return new Response("FAILED", "category id was not found");

        Product product = productOptional.get();
        product.setCategory(categoryOptional.get());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setPhoto(productDTO.getPhoto());

        return new Response("SUCCESS", "product was updated");
    }

    @Override
    public Response deleteProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            return new Response("FAILED", "Product id was not found");
        productRepository.deleteById(id);
        return new Response("SUCCESS", "product was deleted");

    }

    @Override
    public Response high_demand_products() {
        List<HighDemandProducts> highDemandProducts = new ArrayList<>();
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            Integer orderCountByProductId = orderRepository.getOrderCountByProductId(product.getId());
            if (orderCountByProductId > 10) {
                highDemandProducts.add(new HighDemandProducts(product.getId(), orderCountByProductId));
            }
        }
        return new Response("SUCCESS", highDemandProducts);
    }

    @Override
    public Response number_of_products_in_ye() {
        List<OrdersByCountry> ordersByCountries = new ArrayList<>();
        List<String> countryCodes = customerRepository.getCountryCodes();
        for (String countryCode : countryCodes) {
            Integer countOrdersByCountry = orderRepository.getCountOrdersByCountry(countryCode);
                if (countOrdersByCountry>0)
                    ordersByCountries.add(new OrdersByCountry(countOrdersByCountry,countryCode));
        }
        return new Response("SUCCESS",ordersByCountries);
    }

    @Override
    public Response bulk_products() {
        List<BulkProducts> bulkProducts = new ArrayList<>();
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            Integer orderCountByProductId = orderRepository.getOrderCountByProductId(product.getId());
            if (orderCountByProductId>=8)
                bulkProducts.add(new BulkProducts(product.getId(),product.getPrice()));
        }
        return new Response("SUCCESS",bulkProducts);
    }
}
