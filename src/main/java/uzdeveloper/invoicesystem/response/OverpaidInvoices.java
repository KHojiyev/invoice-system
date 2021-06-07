package uzdeveloper.invoicesystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverpaidInvoices {

    private Integer invoiceId;
    private Double reimbursedAmount;
    private Double paid;



}
