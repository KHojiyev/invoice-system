package uzdeveloper.invoicesystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkProducts {

    private Integer productId;
    private Double price;


}
