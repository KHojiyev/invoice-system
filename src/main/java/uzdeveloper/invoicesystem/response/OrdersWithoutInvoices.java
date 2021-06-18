package uzdeveloper.invoicesystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersWithoutInvoices {

    private Integer id;
    private LocalDate date;
    private Double price;
    private Integer quantity;


}
