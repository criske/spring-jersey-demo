package pcf.crskdev.jersey.demo.common.repositories;

import pcf.crskdev.jersey.demo.common.enitities.Student;

import java.util.Optional;

public interface StudentRepository {

    Optional<Student> findOne(final String username);

    Student create(final Student student);

    Student update(final Student student);

    boolean exists(final String username);
}
