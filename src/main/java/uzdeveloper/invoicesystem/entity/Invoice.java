package uzdeveloper.invoicesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Order order;

    @Column(length = 8,scale = 2,nullable = false,columnDefinition = "numeric")
    private Double amount;

    @Column(name = "issued",nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate due;


}
