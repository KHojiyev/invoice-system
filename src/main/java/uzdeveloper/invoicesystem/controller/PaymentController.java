package uzdeveloper.invoicesystem.controller;

import org.springframework.web.bind.annotation.*;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.PaymentDTO;
import uzdeveloper.invoicesystem.service.implement.PaymentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    final PaymentServiceImpl paymentServiceImpl;

    public PaymentController(PaymentServiceImpl paymentServiceImpl) {
        this.paymentServiceImpl = paymentServiceImpl;
    }


    @GetMapping("/list")
    public Response getAllPayments() {
        return paymentServiceImpl.getAllPayments();
    }

    @GetMapping("/details")
    public Response getOnePayment(@RequestParam Integer id) {
        return paymentServiceImpl.getOnePayment(id);
    }

    @PostMapping("/one")
    public Response addPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentServiceImpl.addPayment(paymentDTO);
    }

    @PostMapping("/list")
    public Response addPayments(@RequestBody List<PaymentDTO> paymentsDTO) {
        return paymentServiceImpl.addPayments(paymentsDTO);
    }

    @PostMapping
    public Response postByInvoiceId(@RequestParam Integer invoice_id) {
        return paymentServiceImpl.postByInvoiceId(invoice_id);
    }

    @PutMapping("/{id}")
    public Response updatePayment(@PathVariable Integer id, @RequestBody PaymentDTO paymentDTO) {
        return paymentServiceImpl.updatePayment(id, paymentDTO);
    }

    public Response deletePayment(@PathVariable Integer id) {
        return paymentServiceImpl.deletePayment(id);
    }
}
