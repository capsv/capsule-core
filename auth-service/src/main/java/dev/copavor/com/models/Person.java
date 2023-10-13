package dev.copavor.com.models;

import dev.copavor.com.common.Role;
import dev.copavor.com.models.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person extends CommonEntity {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "owner",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Details info;
}
