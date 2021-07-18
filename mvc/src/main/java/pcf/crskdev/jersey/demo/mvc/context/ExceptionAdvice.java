package pcf.crskdev.jersey.demo.mvc.context;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pcf.crskdev.jersey.demo.common.CommonException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Map<String, Object>> handleCommonExceptions(CommonException exception){
        return ResponseEntity.status(exception.getCode()).body(exception.getBody());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(ConstraintViolationException exception){
        return exception.getConstraintViolations().stream()
            .reduce(new HashMap<>(), (acc, curr) -> {
                acc.put(
                    curr.getPropertyPath().toString(),
                    curr.getMessage()
                );
                return acc;
            }, (acc, curr) -> acc);
    }
}
