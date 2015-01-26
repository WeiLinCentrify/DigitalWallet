package wallet;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Malformed request")  // 400
public class BadRequestException extends RuntimeException {
  public BadRequestException(String err) {
     super(err);
  }
}

