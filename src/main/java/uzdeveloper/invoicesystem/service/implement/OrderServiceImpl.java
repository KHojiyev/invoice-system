package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.OrdersWithoutInvoices;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.OrderDTO;
import uzdeveloper.invoicesystem.dto.OrderDetailsDTO;
import uzdeveloper.invoicesystem.entity.*;
import uzdeveloper.invoicesystem.repository.*;
import uzdeveloper.invoicesystem.service.OrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {


    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final ProductRepository productRepository;
    final DetailRepository detailRepository;
    final InvoiceRepository invoiceRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, DetailRepository detailRepository, InvoiceRepository invoiceRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;

        this.detailRepository = detailRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Response getAllOrders() {
        if (orderRepository.findAll().isEmpty())
            return new Response("FAILED");
        return new Response("SUCCESS",orderRepository.findAll());

    }

    @Override
    public Response getOneOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty())
            return new Response("FAILED","such order id was not found");
        return new Response("SUCCESS",orderOptional.get());

    }
    @Override
    public Response addOrder(OrderDTO orderDTO) {
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        if (customerOptional.isEmpty())
            return new Response("FAILED","Such customer id was not found");
        Order order = new Order();
        order.setDate(orderDTO.getDate());
        order.setCustomer(customerOptional.get());
        orderRepository.save(order);

        return new Response("SUCCESS","Order was added");

    }
    @Override
    public Response addOrders(List<OrderDTO> orderDTO) {
        for (OrderDTO dto : orderDTO) {
            Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());
            if (customerOptional.isEmpty())
                return new Response("FAILED","Such customer id was not found");
            Order order = new Order();
            order.setDate(dto.getDate());
            order.setCustomer(customerOptional.get());
            orderRepository.save(order);
        }
        return new Response("SUCCESS","Orders were added");
    }
    @Override
    public Response updateOrder(Integer id, OrderDTO orderDTO) {
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        if (customerOptional.isEmpty())
            return new Response("FAILED","Such customer id was not found");

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty())
            return new Response("FAILED","such order id was not found");

        Order order = orderOptional.get();
        order.setCustomer(customerOptional.get());
        order.setDate(orderDTO.getDate());
        return  new Response("SUCCESS","order was updated");


    }
    @Override
    public Response deleteOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty())
            return new Response("FAILED","such order id was not found");
        orderRepository.deleteById(id);
        return  new Response("SUCCESS","order was deleted");
    }

    @Override
    public Response orders_without_details() {
        List<Order> orders = orderRepository.orders_without_details();
        return new Response("SUCCESS",orders);
    }

    @Override
    public Response postOrderDetails(OrderDetailsDTO orderDetailsDTO) {
        Optional<Product> optionalProduct = productRepository.findById(orderDetailsDTO.getProductId());
        if (optionalProduct.isEmpty())
            return new Response("FAILED","such product id was not found");
        Optional<Customer> optionalCustomer = customerRepository.findById(orderDetailsDTO.getCustomerId());
        if (optionalCustomer.isEmpty())
            return new Response("FAILED","such customer id was not found");

        Order order = new Order();
        LocalDate localDate = LocalDate.now();
        order.setDate(localDate);
        order.setCustomer(optionalCustomer.get());
        Order order1 = orderRepository.saveAndFlush(order);

        Detail detail = new Detail();
        detail.setProduct(optionalProduct.get());
        detail.setOrder(order1);
        detail.setQuantity(orderDetailsDTO.getQuantity().shortValue());
        Detail detail1 = detailRepository.saveAndFlush(detail);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setDue(LocalDate.now().plusMonths(6));
        invoice.setIssueDate(localDate);
        invoice.setAmount((double)orderDetailsDTO.getQuantity());
        Integer invoice1 = invoiceRepository.saveAndFlush(invoice).getId();
        return new Response("SUCCESS",(Integer)invoice1);


    }

    @Override
    public Response orders_without_invoices() {
        List<OrdersWithoutInvoices> ordersWithoutInvoices = new ArrayList<>();
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            Order ordersByOrderId = orderRepository.getOrdersByOrderId(order.getId());
            if (ordersByOrderId==null) {
                List<Detail> listOfDetailsByOrderId = detailRepository.getListOfDetailsByOrderId(order.getId());
                if (detailRepository.getListOfDetailsByOrderId(order.getId())!=null){
                    double price = 0;
                    for (Detail detail : listOfDetailsByOrderId) {
                        Product product = detail.getProduct();
                        price+=product.getPrice();
                    }
                    ordersWithoutInvoices.add(new OrdersWithoutInvoices(order.getId(),order.getDate(),price));
                }

            }
        }
        return new Response("SUCCESS",ordersWithoutInvoices);

    }
}
