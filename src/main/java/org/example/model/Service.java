package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "services", schema = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column()
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
