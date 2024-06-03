package email.verify.service.utils.mappers;

import email.verify.service.dtos.kafka.VerifyDTO;
import email.verify.service.models.Verify;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerifyMapper {

    VerifyDTO convertToDTO(Verify verify);
}