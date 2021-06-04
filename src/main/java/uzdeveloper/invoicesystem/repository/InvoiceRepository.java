package uzdeveloper.invoicesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uzdeveloper.invoicesystem.entity.Invoice;
import uzdeveloper.invoicesystem.entity.Payment;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

   @Query(value = "select  i from Invoice i join Payment p on i.id=p.invoice.id where i.amount > p.amount and i.due <= ?1 ")
    List<Invoice> getAllIssueInvoices(LocalDate localDate);

   @Query(value = "select i from Invoice i join Order o on o.id=i.order.id where i.issueDate <= o.date")
    List<Invoice> wrong_date_invoices();






}
