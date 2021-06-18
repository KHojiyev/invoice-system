package uzdeveloper.invoicesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uzdeveloper.invoicesystem.entity.Invoice;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    //private Timestamp time;
    private Double amount;
    private Integer invoiceId;


}
