package uzdeveloper.invoicesystem.service.implement;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.PaymentDTO;
import uzdeveloper.invoicesystem.entity.Invoice;
import uzdeveloper.invoicesystem.entity.Payment;
import uzdeveloper.invoicesystem.repository.InvoiceRepository;
import uzdeveloper.invoicesystem.repository.PaymentRepository;
import uzdeveloper.invoicesystem.service.PaymentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Response getAllPayments() {
        if (paymentRepository.findAll().isEmpty())
            return new Response("FAILED", "table is empty");
        return new Response("SUCCESS", paymentRepository.findAll());
    }

    @Override
    public Response getOnePayment(Integer id) {
        if (paymentRepository.findById(id).isEmpty())
            return new Response("FAILED", "such payment id was not found");
        return new Response("SUCCESS", paymentRepository.findById(id).get());


    }

    @Override
    public Response addPayment(PaymentDTO paymentDTO) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(paymentDTO.getInvoiceId());
        if (invoiceOptional.isEmpty())
            return new Response("FAILED", "such invoice id was not found");
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setInvoice(invoiceOptional.get());
        paymentRepository.save(payment);
        return new Response("SUCCESS", "payment was added");
    }

    @Override
    public Response addPayments(List<PaymentDTO> paymentsDTO) {
        for (PaymentDTO paymentDTO : paymentsDTO) {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(paymentDTO.getInvoiceId());
            if (invoiceOptional.isEmpty())
                return new Response("FAILED", "such invoice id was not found");
            Payment payment = new Payment();
            payment.setAmount(paymentDTO.getAmount());
            payment.setTime(Timestamp.valueOf(LocalDateTime.now()));
            payment.setInvoice(invoiceOptional.get());
            paymentRepository.save(payment);
        }
        return new Response("SUCCESS", "payments were added");

    }

    @Override
    public Response updatePayment(Integer id, PaymentDTO paymentDTO) {
        if (paymentRepository.findById(id).isEmpty())
            return new Response("FAILED", "such payment id was not found");
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(paymentDTO.getInvoiceId());
        if (invoiceOptional.isEmpty())
            return new Response("FAILED", "such invoice id was not found");
        Payment payment = paymentRepository.findById(id).get();
        payment.setInvoice(invoiceOptional.get());
        payment.setTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setAmount(paymentDTO.getAmount());

        return new Response("SUCCESS", "payment was updated");

    }

    @Override
    public Response deletePayment(Integer id) {
        if (paymentRepository.findById(id).isEmpty())
            return new Response("FAILED", "such payment id was not found");
        paymentRepository.deleteById(id);

        return new Response("SUCCESS", "payment was deleted");
    }

    @Override
    public Response postByInvoiceId(Integer invoice_id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoice_id);
        if (invoiceOptional.isEmpty())
            return new Response("FAILED","such invoice id was not found");
        Payment payment = new Payment();
        payment.setInvoice(invoiceOptional.get());
        payment.setAmount(invoiceOptional.get().getAmount());
        payment.setTime(Timestamp.valueOf(LocalDateTime.now()));
        paymentRepository.save(payment);

        return new Response("SUCCESS",paymentRepository.findAll());
    }
}
