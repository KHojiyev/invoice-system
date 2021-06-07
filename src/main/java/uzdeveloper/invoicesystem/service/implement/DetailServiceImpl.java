package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.DetailDTO;
import uzdeveloper.invoicesystem.entity.Detail;
import uzdeveloper.invoicesystem.entity.Order;
import uzdeveloper.invoicesystem.entity.Product;
import uzdeveloper.invoicesystem.repository.DetailRepository;
import uzdeveloper.invoicesystem.repository.OrderRepository;
import uzdeveloper.invoicesystem.repository.ProductRepository;
import uzdeveloper.invoicesystem.service.DetailService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailServiceImpl implements DetailService {

    final DetailRepository detailRepository;
    final OrderRepository orderRepository;
    final ProductRepository productRepository;

    public DetailServiceImpl(DetailRepository detailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.detailRepository = detailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Response getAllDetails() {
        if (detailRepository.findAll().isEmpty())
            return new Response("FAILED", "Table is empty");
        return new Response("SUCCESS", detailRepository.findAll());
    }

    @Override
    public Response getOneDetail(Integer id) {
        Optional<Detail> optionalDetail = detailRepository.findById(id);
        if (optionalDetail.isEmpty())
            return new Response("FAILED", "such detail id was not found");
        return new Response("SUCCESS", optionalDetail.get());
    }

    @Override
    public Response addDetail(DetailDTO detailDTO) {
        Optional<Order> orderOptional = orderRepository.findById(detailDTO.getOrderId());
        if (orderOptional.isEmpty())
            return new Response("FAILED", "Such order id was not found");

        Optional<Product> productOptional = productRepository.findById(detailDTO.getProductId());
        if (productOptional.isEmpty())
            return new Response("FAILED", "Such product id was not found");

        Detail detail = new Detail();

        detail.setOrder(orderOptional.get());
        detail.setProduct(productOptional.get());
        detail.setQuantity(detailDTO.getQuantity());
        detailRepository.save(detail);

        return new Response("SUCCESS", "detail was added");

    }

    @Override
    public Response addDetails(List<DetailDTO> detailDTO) {
        for (DetailDTO dto : detailDTO) {
            Optional<Order> orderOptional = orderRepository.findById(dto.getOrderId());
            if (orderOptional.isEmpty())
                return new Response("FAILED", "Such order id was not found");

            Optional<Product> productOptional = productRepository.findById(dto.getProductId());
            if (productOptional.isEmpty())
                return new Response("FAILED", "Such product id was not found");

            Detail detail = new Detail();
            detail.setOrder(orderOptional.get());
            detail.setProduct(productOptional.get());
            detail.setQuantity(dto.getQuantity());
            detailRepository.save(detail);
        }
        return new Response("SUCCESS", "details were added");
    }

    @Override
    public Response updateDetail(Integer id, DetailDTO detailDTO) {
        Optional<Detail> detailOptional = detailRepository.findById(id);
        if (detailOptional.isEmpty())
            return new Response("FAILED", "Such detail id was not found");

        Optional<Order> orderOptional = orderRepository.findById(detailDTO.getOrderId());
        if (orderOptional.isEmpty())
            return new Response("FAILED", "Such order id was not found");

        Optional<Product> productOptional = productRepository.findById(detailDTO.getProductId());
        if (productOptional.isEmpty())
            return new Response("FAILED", "Such product id was not found");

        Detail detail = detailOptional.get();
        detail.setOrder(orderOptional.get());
        detail.setProduct(productOptional.get());
        detail.setQuantity(detailDTO.getQuantity());
        detailRepository.save(detail);

        return new Response("SUCCESS", "Detail was updated");

    }

    @Override
    public Response deleteDetail(Integer id) {
        Optional<Detail> detailOptional = detailRepository.findById(id);
        if (detailOptional.isEmpty())
            return new Response("FAILED", "Such detail id was not found");
        detailRepository.deleteById(id);
        return new Response("SUCCESS", "Detail was  deleted");
    }


}
