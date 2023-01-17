package com.sparta.team_1_hyogeunchild.exception;

import com.sparta.team_1_hyogeunchild.exception.dto.controllerExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class controllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request){
        controllerExceptionResponseDto apiExceptionResponseDto = new controllerExceptionResponseDto(ex.getFieldError().getDefaultMessage(), 400);
        return new ResponseEntity<>(
                apiExceptionResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //상태코드를 400으로 집어넣어줌
    @ExceptionHandler(IllegalArgumentException.class)
    //ExceptionHandler이다 ()안에 특정 예외클래스를 지정해주면 컨트롤러단에서 해당 예외가 발생했을때 아래의 메소드를 수행하겠다 이말임
    public controllerExceptionResponseDto apiRequestExceptionHandling(IllegalArgumentException exception) {
        log.warn(exception.getMessage());
        return new controllerExceptionResponseDto(exception.getMessage(),400);
    }
}
