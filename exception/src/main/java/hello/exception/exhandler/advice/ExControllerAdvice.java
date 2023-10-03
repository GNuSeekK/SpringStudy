package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api") // 패키지 지정
// @RestControllerAdvice(annotations = RestController.class) Rest인 애들 다
// @RestControllerAdvice("org.example.controllers") 패키지 모두 다
// @RestControllerAdvice(assignableTypes = {ControllerInterface1.class, AbstractController.class})
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        // ErrorResult 가 그대로 json으로 반환된다.
        log.error("[illegalExHandler] ex", e);
        // 정상 흐름이 돼서 상태 코드가 200이 된다.
        // 바꾸고 싶으면 처리를 해줘야 한다 @ResponseStatus
        return new ErrorResult("BAD", e.getMessage());
    }


    // 파라미터로 받은 예외 쓰면 생략 가능
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        // ResponseEntity 를 사용하면 상태 코드를 변경할 수 있다.
        log.error("[userExHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
