package pcf.crskdev.jersey.demo.common.repositories;

import pcf.crskdev.jersey.demo.common.enitities.Student;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Named
public final class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Student> findOne(final String username) {
        return Optional.ofNullable(this.entityManager
            .find(Student.class, username));
    }

    @Override
    public Student create(final Student student) {
        this.entityManager.persist(student);
        return student;
    }

    @Override
    public Student update(final Student student) {
        this.entityManager.persist(student);
        return this.entityManager.getReference(
            Student.class,
            student.getUsername()
        );
    }

    @Override
    public boolean exists(final String username) {
        return this.findOne(username).isPresent();
    }

}
