package uzdeveloper.invoicesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private Integer categoryId;
    private String description;
    private Double price;
    private Integer photoId;

}
