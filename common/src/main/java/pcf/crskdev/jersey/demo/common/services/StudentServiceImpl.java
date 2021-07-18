package pcf.crskdev.jersey.demo.common.services;

import pcf.crskdev.jersey.demo.common.CommonException;
import pcf.crskdev.jersey.demo.common.CommonPasswordEncoder;
import pcf.crskdev.jersey.demo.common.CommonValidated;
import pcf.crskdev.jersey.demo.common.enitities.Student;
import pcf.crskdev.jersey.demo.common.input.StudentInput;
import pcf.crskdev.jersey.demo.common.repositories.StudentRepository;
import pcf.crskdev.jersey.demo.common.util.BeanMerger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Named
@Transactional
@CommonValidated
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    private final CommonPasswordEncoder encoder;

    @Inject
    public StudentServiceImpl(
        final StudentRepository repository,
        final CommonPasswordEncoder encoder
    ) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    @RolesAllowed("ROLE_STUDENT")
    public Student me(final String principal) {
        final var me = this.repository
            .findOne(principal)
            .orElseThrow(() -> CommonException.BadRequest("Principal not " +
                "found " +
                "in storage"));
        return this.sanitize(me);
    }

    @Override
    @RolesAllowed("ROLE_STUDENT")
    public Student update(final String principal,
                          final @Valid StudentInput input) {
        final var me = this.repository
            .findOne(principal)
            .orElseThrow(() -> CommonException.BadRequest("Principal not " +
                "found " +
                "in storage"));
        final var updated = input.toStudent(this.encoder);
        final var errors = BeanMerger.merge(me, updated, "username");
        if (!errors.isEmpty()) {
            final var errorPropOutput = String.join(",", errors);
            final var are = errors.size() == 1 ? " is" : "are";
            throw CommonException.BadRequest(errorPropOutput + are + " not " +
                "allowed to be updated");
        }
        return this.sanitize(this.repository.update(me));
    }

    @Override
    public Student register(final @Valid StudentInput input) {
        final var exists = this.repository.exists(input.getUsername());
        if (exists) {
            throw CommonException.BadRequest("Username '" + input.getUsername() + "' is already registered");
        }
        final var created = this.repository.create(
            new Student(
                input.getUsername(),
                input.getFirstName(),
                input.getLastName(),
                this.encoder.encode(input.getPassword())
            )
        );
        return this.sanitize(created);
    }

    //TODO: use a proxy instead?
    private Student sanitize(final Student student) {
        return new Student(student.getUsername(), student.getFirstName(),
            student.getLastName(), "************"
        );
    }
}
