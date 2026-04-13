package spring.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "motorcycles")
public class Motorcycle implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String model;
    private Integer year;
    @Column(name = "engine_cc")
    private Integer engine;
    private BigDecimal price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "manufacturers_id")
    private Manufacturer manufacturer;

    @OneToMany(
            mappedBy = "motorcycles",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();
}
