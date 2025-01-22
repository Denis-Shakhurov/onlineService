package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "service")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 30)
    private String role;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
