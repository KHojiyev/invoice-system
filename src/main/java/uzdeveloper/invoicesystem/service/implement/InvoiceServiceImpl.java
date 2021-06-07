package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.OverpaidInvoices;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.InvoiceDTO;
import uzdeveloper.invoicesystem.entity.Invoice;
import uzdeveloper.invoicesystem.entity.Order;
import uzdeveloper.invoicesystem.repository.InvoiceRepository;
import uzdeveloper.invoicesystem.repository.OrderRepository;
import uzdeveloper.invoicesystem.repository.PaymentRepository;
import uzdeveloper.invoicesystem.service.InvoiceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    final InvoiceRepository invoiceRepository;
    final OrderRepository orderRepository;
    final PaymentRepository paymentRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Response getAllInvoices() {
        if (invoiceRepository.findAll().isEmpty())
            return new Response("FAILED", "Table is empty");
        return new Response("SUCCESS", invoiceRepository.findAll());
    }

    @Override
    public Response getOneInvoice(Integer id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty())
            return new Response("FAILED", "Such invoice id was not found");
        return new Response("SUCCESS", invoiceOptional.get());
    }

    @Override
    public Response addInvoice(InvoiceDTO invoiceDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(invoiceDTO.getOrderId());
        if (optionalOrder.isEmpty())
            return new Response("FAILED", "Such order id was not found");
        Invoice invoice = new Invoice();
        invoice.setOrder(optionalOrder.get());
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setDue(invoiceDTO.getDue());
        invoice.setIssueDate(invoiceDTO.getIssueDate());
        invoiceRepository.save(invoice);
        return new Response("SUCCESS", "Invoice was added");
    }

    @Override
    public Response addInvoices(List<InvoiceDTO> invoiceDTO) {
        for (InvoiceDTO dto : invoiceDTO) {
            Optional<Order> optionalOrder = orderRepository.findById(dto.getOrderId());
            if (optionalOrder.isEmpty())
                return new Response("FAILED", "Such order id was not found");
            Invoice invoice = new Invoice();
            invoice.setOrder(optionalOrder.get());
            invoice.setAmount(dto.getAmount());
            invoice.setDue(dto.getDue());
            invoice.setIssueDate(dto.getIssueDate());
            invoiceRepository.save(invoice);
        }
        return new Response("SUCCESS", "Invoice was added");
    }

    @Override
    public Response updateInvoice(Integer id, InvoiceDTO invoiceDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(invoiceDTO.getOrderId());
        if (optionalOrder.isEmpty())
            return new Response("FAILED", "Such order id was not found");
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty())
            return new Response("FAILED", "Such invoice id was not found");
        Invoice invoice = invoiceOptional.get();
        invoice.setOrder(optionalOrder.get());
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setIssueDate(invoiceDTO.getIssueDate());
        invoice.setDue(invoiceDTO.getDue());

        return new Response("SUCCESS", "Invoice was updated");
    }

    @Override
    public Response deleteInvoice(Integer id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty())
            return new Response("FAILED", "Such invoice id was not found");
        invoiceRepository.deleteById(id);
        return new Response("SUCCESS", "Invoice was deleted");

    }

    @Override
    public Response getAllExpired() {
        List<Invoice> invoiceList = invoiceRepository.getAllIssueInvoices(LocalDate.now());
        return new Response("SUCCESS", invoiceList);
    }

    @Override
    public Response wrong_date_invoices() {
        List<Invoice> invoices = invoiceRepository.wrong_date_invoices();
        return new Response("SUCCESS", invoices);
    }

    @Override
    public Response overpaid_invoices() {
        List<OverpaidInvoices> invoices = new ArrayList<>();
        List<Invoice> all = invoiceRepository.findAll();
        List<Integer> byInvoice = paymentRepository.getByInvoice();
        for (Integer integer : byInvoice) {
            Invoice invoice = invoiceRepository.findById(integer).get();
            double reimbursed =0;
            Double byInvoiceId = paymentRepository.getByInvoiceId(invoice.getId());

            if (invoice.getAmount()<byInvoiceId)
                invoices.add(new OverpaidInvoices(invoice.getId(),invoice.getAmount(),byInvoiceId));
        }

        return new Response("SUCCESS",invoices);
    }



}


