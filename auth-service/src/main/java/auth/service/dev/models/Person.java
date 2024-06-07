package auth.service.dev.models;

import auth.service.dev.common.Role;
import auth.service.dev.models.common.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "is_confirm")
    private boolean isConfirm;

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email
            + '\'' + ", password='" + password + '\'' + ", role=" + role + '}';
    }
}