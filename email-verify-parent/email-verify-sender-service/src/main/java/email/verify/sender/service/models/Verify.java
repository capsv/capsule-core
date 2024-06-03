package email.verify.sender.service.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Verify {

    private long id;

    private String username;

    private String email;

    private int code;

    private LocalDateTime createdAt;

}
