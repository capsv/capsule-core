package auth.service.dev.dtos.responses.entities;

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
public class Credentials {

    private String username;

    private boolean confirm; //TODO почему isConfirm возвращается в JSON как confirm

    //TODO authorities
}