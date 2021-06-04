package uzdeveloper.invoicesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uzdeveloper.invoicesystem.entity.Order;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Integer orderId;
    private Double amount;
    private LocalDate issueDate;
    private LocalDate due;



}
