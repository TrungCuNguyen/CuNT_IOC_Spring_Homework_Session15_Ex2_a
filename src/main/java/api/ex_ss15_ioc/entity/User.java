package api.ex_ss15_ioc.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String role; // ROLE_USER hoặc ROLE_ADMIN
}
