package profile.service.dev.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import profile.service.dev.models.common.Entity;

@jakarta.persistence.Entity
@Table(name = "details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Details implements Entity {

    @Id
    @Column(name = "details_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Details{username='" + username + "', firstName='"
            + firstName + "' secondName='" + secondName + "', age=" + age
            + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
