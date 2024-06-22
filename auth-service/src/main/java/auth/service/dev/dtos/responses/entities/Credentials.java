package auth.service.dev.dtos.responses.entities;

import auth.service.dev.dtos.CommonDTO;
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
public class Credentials extends CommonDTO {

    private String username;

    private String email;

    private boolean confirm; //TODO почему isConfirm возвращается в JSON как confirm

    private boolean assay;
    //TODO authorities
}