package email.verify.service.controllers.interfaces;

import email.verify.service.dtos.requests.UserInfoReqst;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IVerifyController {

    /**
     * Метод должен принимать запрос на верификацию, генерировать код, сохранять его в бд и
     * отправлять запрос в кафку к @email-verify-send-service на отправку этого кода пользователю на
     * email. Чтобы узнать куда отправлять он должен обратиться к @account-management-service за
     * почтой пользователя
     */
    @PostMapping()
    ResponseEntity<HttpStatus> post(@RequestBody @Valid UserInfoReqst info,
        BindingResult bindingResult);

    /**
     * Метод сравнивает код, который пришел от пользователя, с кодом в бд для этого пользователя,
     * если они совпадают сервис подтверждает верификацию
     */
    ResponseEntity<HttpStatus> verify();
}