package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.entity.Customer;
import uzdeveloper.invoicesystem.entity.Order;
import uzdeveloper.invoicesystem.repository.CustomerRepository;
import uzdeveloper.invoicesystem.repository.OrderRepository;
import uzdeveloper.invoicesystem.response.LastOrderByCustomer;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;
    final OrderRepository orderRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Response getAllCustomers() {
        if (customerRepository.findAll().isEmpty())
            return new Response("FAILED");
        return new Response("SUCCESS", customerRepository.findAll());
    }

    @Override
    public Response getOneCustomer(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            return new Response("FAILED", "such customer id was not found");
        return new Response("SUCCESS", customer.get());
    }

    @Override
    public Response addCustomer(Customer customer) {
        Customer customer1 = new Customer();
        customer1.setName(customer.getName());
        customer1.setAddress(customer.getAddress());
        customer1.setCountry(customer.getCountry());
        customer1.setPhone(customer.getPhone());
        customerRepository.save(customer1);

        return new Response("SUCCESS", "Customer was added");

    }

    @Override
    public Response addCustomers(List<Customer> customer) {
        for (Customer customer1 : customer) {
            Customer customer2 = new Customer();
            customer2.setName(customer1.getName());
            customer2.setAddress(customer1.getAddress());
            customer2.setCountry(customer1.getCountry());
            customer2.setPhone(customer1.getPhone());
            customerRepository.save(customer2);
        }

        return new Response("SUCCESS", "CUSTOMERS WERE ADDED");

    }

    @Override
    public Response updateCustomer(Integer id, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty())
            return new Response("FAILED", "such customer id was not found");
        Customer customer1 = customerOptional.get();

        customer1.setPhone(customer.getPhone());
        customer1.setCountry(customer.getCountry());
        customer1.setAddress(customer.getAddress());
        customer1.setName(customer.getName());
        return new Response("SUCCESS", " Customer was updated");
    }

    @Override
    public Response customers_without_orders() {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> customersWithoutOrders = new ArrayList<>();
        List<Integer> allCustomerIds = orderRepository.getAllCustomerIds();
        for (Customer customer : customers) {
            if (!allCustomerIds.contains(customer.getId()))
                customersWithoutOrders.add(customer);
        }

        return new Response("SUCCESS", customersWithoutOrders);
    }

    @Override
    public Response customers_last_orders() {

        List<Customer> all = customerRepository.findAll();
        List<LastOrderByCustomer> infos = new ArrayList<>();
        List<Integer> allCustomerIds = orderRepository.getAllCustomerIds();
        for (Integer allCustomerId : allCustomerIds) {
            Customer customer = customerRepository.findById(allCustomerId).get();

            List<Order> lastOrderByCustomerId = orderRepository.getLastOrderByCustomerId(customer.getId());
            for (Order order : lastOrderByCustomerId) {
                infos.add(new LastOrderByCustomer(
                        customer.getId(),
                        customer.getName(),
                        order.getDate()));
                break;
            }

        }


        return new Response("SUCCESS", infos);
    }

    @Override
    public Response deleteCustomer(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty())
            return new Response("FAILED", "such customer id was not found");
        customerRepository.deleteById(id);
        return new Response("SUCCESS", " Customer  was deleted");
    }
}
