package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "services", schema = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column()
    private Double price;

    @OneToMany
    @JoinColumn(name = "service_id")
    private List<Order> orders;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
