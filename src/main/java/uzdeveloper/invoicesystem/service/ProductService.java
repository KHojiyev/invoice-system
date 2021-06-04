package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.ProductDTO;

import java.util.List;

@Service
public interface ProductService {

    public Response getAllProducts();
    public Response getOneProduct(Integer id);
    public Response addProduct(ProductDTO productDTO);
    public Response addProducts(List<ProductDTO> productsDTO);
    public Response updateProduct(Integer id, ProductDTO productDTO);
    public Response deleteProduct(Integer id);


    public Response high_demand_products();

    public Response number_of_products_in_ye();

    public Response bulk_products();

}
