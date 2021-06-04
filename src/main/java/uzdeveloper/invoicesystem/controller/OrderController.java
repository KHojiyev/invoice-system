package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.OrderDTO;
import uzdeveloper.invoicesystem.dto.OrderDetailsDTO;
import uzdeveloper.invoicesystem.service.implement.OrderServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping
    public Response getAllOrder() {
        return orderServiceImpl.getAllOrders();
    }

    @GetMapping("/details")
    public Response getOneOrder(@RequestParam Integer id) {
        return orderServiceImpl.getOneOrder(id);
    }

    @PostMapping("/one")
    public Response addOrder(@RequestBody OrderDTO orderDTO) {
        return orderServiceImpl.addOrder(orderDTO);
    }

    @PostMapping("/list")
    public Response addOrders(@RequestBody List<OrderDTO> orderDTO) {
        return orderServiceImpl.addOrders(orderDTO);
    }

    @PutMapping("/{id}")
    public Response updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        return orderServiceImpl.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public Response deleteOrder(@PathVariable Integer id) {
        return orderServiceImpl.deleteOrder(id);
    }

    @GetMapping("/orders_without_details")
    public Response orders_without_details() {
        return orderServiceImpl.orders_without_details();
    }

    @PostMapping
    public Response postOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO){
        return orderServiceImpl.postOrderDetails(orderDetailsDTO);
    }

    @GetMapping("/orders_without_invoices") // query did not return a unique result: 3
    public Response orders_without_invoices(){
        return orderServiceImpl.orders_without_invoices();
    }



}
