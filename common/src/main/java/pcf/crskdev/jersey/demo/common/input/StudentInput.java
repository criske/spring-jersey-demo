package pcf.crskdev.jersey.demo.common.input;

import pcf.crskdev.jersey.demo.common.CommonPasswordEncoder;
import pcf.crskdev.jersey.demo.common.enitities.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class StudentInput {

    @NotBlank
    private final String username;

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @Size(min = 3, max = 16, message = "Password must be at least 3 " +
        "characters and at most 16")
    private final String password;

    public StudentInput(
        final String username,
        final String firstName,
        final String lastName,
        final String password
    ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Student toStudent(final CommonPasswordEncoder encoder) {
        final Student student = new Student();
        student.setUsername(this.username);
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        if (this.password != null) {
            student.setPasswordHash(encoder.encode(this.password));
        }
        return student;
    }
}
