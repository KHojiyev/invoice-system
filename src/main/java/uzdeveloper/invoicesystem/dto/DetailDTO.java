package uzdeveloper.invoicesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
import uzdeveloper.invoicesystem.entity.Order;
import uzdeveloper.invoicesystem.entity.Product;

import javax.persistence.Column;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDTO {

    private Integer orderId;
    private Integer productId;
    private short quantity;

}
