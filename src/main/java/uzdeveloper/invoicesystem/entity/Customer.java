package uzdeveloper.invoicesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 14,nullable = false)
    private String name;

    @Column(length = 3,nullable = false,columnDefinition = "CHAR(3)")
    private String country;

    private String address;

    @Column(length = 50)
    private String phone;
}
