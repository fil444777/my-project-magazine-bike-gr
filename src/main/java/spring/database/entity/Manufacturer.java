package spring.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"motorcycle"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Builder.Default
    @OneToMany(
            mappedBy = "manufacturer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Motorcycle> motorcycle = new ArrayList<>();
}
