package pcf.crskdev.jersey.demo.jaxrs.context.mappers;

import pcf.crskdev.jersey.demo.common.CommonException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class CommonExceptionMapper implements ExceptionMapper<CommonException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(CommonException exception) {
        return Response.status(exception.getCode()).entity(exception.getBody()).build();
    }
}
