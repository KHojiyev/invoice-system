package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.InvoiceDTO;
import uzdeveloper.invoicesystem.service.implement.InvoiceServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {


    final InvoiceServiceImpl invoiceServiceImpl;


    public InvoiceController(InvoiceServiceImpl invoiceServiceImpl) {
        this.invoiceServiceImpl = invoiceServiceImpl;
    }

    @GetMapping("/expired_invoices")
    public Response getAllExpired() {
        return invoiceServiceImpl.getAllExpired();
    }

    @GetMapping("/wrong_date_invoices")
    public Response getWrong_date_invoices() {
        return invoiceServiceImpl.wrong_date_invoices();
    }

    @GetMapping
    public Response getAllInvoices() {
        return invoiceServiceImpl.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Response getOneInvoice(@PathVariable Integer id) {
        return invoiceServiceImpl.getOneInvoice(id);
    }

    @PostMapping("/one")
    public Response addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceServiceImpl.addInvoice(invoiceDTO);
    }

    @PostMapping
    public Response addInvoices(@RequestBody List<InvoiceDTO> invoiceDTO) {
        return invoiceServiceImpl.addInvoices(invoiceDTO);
    }

    @PutMapping("/{id}")
    public Response updateInvoice(@PathVariable Integer id, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceServiceImpl.updateInvoice(id, invoiceDTO);
    }

    public Response deleteInvoice(@PathVariable Integer id) {
        return invoiceServiceImpl.deleteInvoice(id);
    }

    @GetMapping("/overpaid_invoices") // problem code 500
    public Response overpaid_invoices() {
        return invoiceServiceImpl.overpaid_invoices();
    }


}
