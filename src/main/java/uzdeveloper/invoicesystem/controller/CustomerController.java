package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.entity.Customer;
import uzdeveloper.invoicesystem.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public Response getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Response getOneCustomer(@PathVariable Integer id){
        return customerService.getOneCustomer(id);
    }

    @PostMapping("/one")
    public Response addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @PostMapping
    public Response addCustomers(@RequestBody List<Customer> customer){
        return customerService.addCustomers(customer);
    }

    @PutMapping("/{id}")
    public Response updateCustomer(@PathVariable Integer id,@RequestBody Customer customer){
        return customerService.updateCustomer(id,customer);

    }

    @DeleteMapping("/{id}")
    public Response deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomer(id);
    };

    @GetMapping("/customers_without_orders")
    public Response customers_without_orders(){
        return customerService.customers_without_orders();
    }

    @GetMapping("/customers_last_orders")
    public Response customers_last_orders(){
        return customerService.customers_last_orders();}
}
