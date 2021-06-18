package uzdeveloper.invoicesystem.service;

import org.springframework.stereotype.Service;
import uzdeveloper.invoicesystem.response.Response;
import uzdeveloper.invoicesystem.dto.InvoiceDTO;

import java.util.List;

@Service
public interface InvoiceService {


    public Response getAllInvoices();

    public Response getOneInvoice(Integer id);

    public Response addInvoice(InvoiceDTO invoiceDTO);

    public Response addInvoices(List<InvoiceDTO> invoiceDTO);

    public Response updateInvoice(Integer id, InvoiceDTO invoiceDTO);

    public Response deleteInvoice(Integer id);

    public Response getAllExpired();

    public Response wrong_date_invoices();

    public Response overpaid_invoices();


}
