package uzdeveloper.invoicesystem.service;


import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.OrderDTO;
import uzdeveloper.invoicesystem.dto.OrderDetailsDTO;

import java.util.List;

@Service
public interface OrderService {

    public Response getAllOrders();

    public Response getOneOrder(Integer id);

    public Response addOrder(OrderDTO orderDTO);

    public Response addOrders(List<OrderDTO> orderDTO);

    public Response updateOrder(Integer id, OrderDTO orderDTO);

    public Response deleteOrder(Integer id);

    public Response orders_without_details();

    public  Response postOrderDetails(OrderDetailsDTO orderDetailsDTO);

    public Response orders_without_invoices();
}
