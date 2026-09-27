package api.ex_ss15_ioc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //

    private String name; //

    @Column(columnDefinition = "TEXT")
    private String description; //

    private BigDecimal price; //

    private String size; //

    private String toppings; //
}
