package email.verify.service.controllers.interfaces;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public interface IVerifyController {

    /**
     * Метод должен принимать запрос на верификацию, генерировать код, сохранять его в бд и
     * отправлять запрос в кафку к @email-verify-send-service на отправку этого кода пользователю на
     * email. Чтобы узнать куда отправлять он должен обратиться к @account-management-service за
     * почтой пользователя
     */
    ResponseEntity<HttpStatusCode> get();

    /**
     * Метод сравнивает код, который пришел от пользователя, с кодом в бд для этого пользователя,
     * если они совпадают сервис подтверждает верификацию
     */
    ResponseEntity<HttpStatusCode> verify();
}