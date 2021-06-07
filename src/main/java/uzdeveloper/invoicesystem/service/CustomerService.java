package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Customer;

import java.util.List;

@Service
public interface CustomerService {

    public Response getAllCustomers();

    public Response getOneCustomer(Integer id);

    public Response addCustomer(Customer customer);

    public Response deleteCustomer(Integer id);

    public Response addCustomers(List<Customer> customer);

    public Response updateCustomer(Integer id, Customer customer);

    public Response customers_without_orders();

    public  Response customers_last_orders();
}
