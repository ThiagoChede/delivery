package br.com.ciet.delivery.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
@Slf4j
public class NotFoundException extends RuntimeException{

  public NotFoundException(){
    this("Recurso não encontrado!");
    log.error("Recurso não encontrado");
  }

  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
