package dev.copavor.com.dtos.responses;

import dev.copavor.com.dtos.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NakedPersonDTO extends CommonDTO {

    private long id;

    private String username;

    private String email;
}
