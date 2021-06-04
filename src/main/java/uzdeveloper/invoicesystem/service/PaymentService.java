package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.PaymentDTO;

import java.util.List;

@Service
public interface PaymentService {

    public Response getAllPayments();
    public Response getOnePayment(Integer id);
    public Response addPayment(PaymentDTO paymentDTO);
    public Response addPayments(List<PaymentDTO> paymentsDTO);
    public Response updatePayment(Integer id, PaymentDTO paymentDTO);
    public Response deletePayment(Integer id);

    public Response postByInvoiceId(Integer invoice_id);
}
