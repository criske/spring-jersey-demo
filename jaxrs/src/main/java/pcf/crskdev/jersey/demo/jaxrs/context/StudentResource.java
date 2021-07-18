package pcf.crskdev.jersey.demo.jaxrs.context;


import pcf.crskdev.jersey.demo.common.services.StudentService;
import pcf.crskdev.jersey.demo.common.enitities.Student;
import pcf.crskdev.jersey.demo.common.input.StudentInput;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class StudentResource {

    private final StudentService service;

    @Inject
    public StudentResource(StudentService service) {
        this.service = service;
    }

    @GET
    public Student me(@Context SecurityContext securityContext) {
        return this.service.me(securityContext.getUserPrincipal().getName());
    }

    @PUT
    public Student update(
        @Context SecurityContext securityContext,
        StudentInput input
    ) {
        return this.service.update(securityContext.getUserPrincipal().getName(), input);
    }

    @POST
    public Student create(StudentInput input) {
        return this.service.register(input);
    }
}
