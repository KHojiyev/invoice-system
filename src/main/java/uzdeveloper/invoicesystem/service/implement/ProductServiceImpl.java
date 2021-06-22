package uzdeveloper.invoicesystem.service.implement;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uzdeveloper.invoicesystem.entity.Attachment;
import uzdeveloper.invoicesystem.repository.*;
import uzdeveloper.invoicesystem.response.AttachmentRepository;
import uzdeveloper.invoicesystem.response.OrdersByCountry;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.response.HighDemandProducts;
import uzdeveloper.invoicesystem.dto.ProductDTO;
import uzdeveloper.invoicesystem.entity.Category;
import uzdeveloper.invoicesystem.entity.Product;
import uzdeveloper.invoicesystem.service.ProductService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DetailRepository detailRepository;
    final AttachmentRepository attachmentRepository;


    public static final String uploadDirectory = "src/main/resources/static/savedFiles/";


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, OrderRepository orderRepository, CustomerRepository customerRepository, DetailRepository detailRepository, AttachmentRepository attachmentRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.detailRepository = detailRepository;
        this.attachmentRepository = attachmentRepository;
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
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getPhotoId());
        if(optionalAttachment.isEmpty())
            return new Response("FAILED", "Attachment id was not found");

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryOptional.get());

        // let's try for list of attachments
        product.setAttachment(optionalAttachment.get());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);
        return new Response("SUCCESS", "product was added");

    }

    @Override
    public Response addProducts(List<ProductDTO> productsDTO) {
        for (ProductDTO productDTO : productsDTO) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
            if (categoryOptional.isEmpty())
                return new Response("FAILED", "category id was not found");
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getPhotoId());
            if(optionalAttachment.isEmpty())
                return new Response("FAILED", "Attachment id was not found");
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setCategory(categoryOptional.get());
            product.setAttachment(optionalAttachment.get());
            product.setPrice(productDTO.getPrice());

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

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getPhotoId());
        if(optionalAttachment.isEmpty())
            return new Response("FAILED", "Attachment id was not found");

        Product product = productOptional.get();
        product.setCategory(categoryOptional.get());
        product.setName(productDTO.getName());
        product.setAttachment(optionalAttachment.get());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);

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
            if (countOrdersByCountry > 0)
                ordersByCountries.add(new OrdersByCountry(countOrdersByCountry, countryCode));
        }
        return new Response("SUCCESS", ordersByCountries);
    }

    // there are two way which return Response , with list<Product> or list< BulkProducts>
    // i used set for saving product only one time
    @Override
    public Response bulk_products() {
        Set<Product> bulkProducts = new HashSet<>();
        List<Product> all = productRepository.findAll();
        List<Integer> productIds = detailRepository.listOfProductIds();
        for (Integer productId : productIds) {
            Product product = productRepository.findById(productId).get();
            bulkProducts.add(product);
        }
        return new Response("SUCCESS", bulkProducts);
    }

    // saving to server by method Post and  MultipartFile
    @SneakyThrows
    @Override
    public HttpEntity<Response> uploadToServer(MultipartFile[] files) {
        for (MultipartFile file : files) {

            if (!file.isEmpty()){
                String originalFilename = file.getOriginalFilename();
                if (originalFilename.isEmpty())
                    return null;
                String[] split = originalFilename.split("\\.");
                String name = UUID.randomUUID() + "." + split[split.length - 1];

                Attachment attachment = new Attachment(
                        originalFilename,
                        file.getSize(),
                        file.getContentType()
                );

                Path path = Paths.get(uploadDirectory +name);
                long copy = Files.copy(file.getInputStream(), path);

                Attachment save = attachmentRepository.save(attachment);

                // fix
                return ResponseEntity.status(true ? HttpStatus.OK : HttpStatus.BAD_GATEWAY).body(new Response("SUCCESS"," "));
            }


        }
        return null;
    }
}
