package pcf.crskdev.jersey.demo.common.services;

import pcf.crskdev.jersey.demo.common.enitities.Student;
import pcf.crskdev.jersey.demo.common.input.StudentInput;

import javax.validation.Valid;

public interface StudentService {

    Student me(final String principal);

    Student update(final String principal, final @Valid StudentInput student);

    Student register(final @Valid StudentInput student);

}

