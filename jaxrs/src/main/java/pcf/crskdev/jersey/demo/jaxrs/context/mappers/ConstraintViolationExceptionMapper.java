package pcf.crskdev.jersey.demo.jaxrs.context.mappers;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public final class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, Object> errors =
            exception.getConstraintViolations().stream()
            .reduce(new HashMap<>(), (acc, curr) -> {
                acc.put(
                    curr.getPropertyPath().toString(),
                    curr.getMessage()
                );
                return acc;
            }, (acc, curr) -> acc);
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(errors)
            .build();
    }
}
