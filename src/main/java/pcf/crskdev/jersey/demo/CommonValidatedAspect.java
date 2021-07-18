package pcf.crskdev.jersey.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Aspect
public class CommonValidatedAspect {

    private final Validator validator;

    public CommonValidatedAspect(final Validator validator) {
        this.validator = validator;
    }

    @Before("@within(pcf.crskdev.jersey.demo.common.CommonValidated) " +
        "and execution(* *(@javax.validation.Valid (*)))")
    public void validationAdvice(JoinPoint jp) {
        Set<ConstraintViolation<?>> violations = Arrays.stream(jp.getArgs())
            .reduce(
                new HashSet<>(),
                (acc, curr) -> {
                    acc.addAll(this.validator.validate(curr));
                    return acc;
                },
                (acc, curr) -> acc // don't care about parallel stuff
            );
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
