package pcf.crskdev.jersey.demo.common.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pcf.crskdev.jersey.demo.common.enitities.Student;

public final class BeanMergerTest {

    @Test
    public void itShouldMergeTwoBeans() {
        var student = new Student();
        student.setFirstName("Foo");
        student.setPasswordHash("123");

        var updater = new Student();
        updater.setLastName("Bar");

        BeanMerger.merge(student, updater);

        MatcherAssert.assertThat(
            student.getFirstName(),
            Matchers.is("Foo")
        );
        MatcherAssert.assertThat(
            student.getLastName(),
            Matchers.is("Bar")
        );
        MatcherAssert.assertThat(
            student.getPasswordHash(),
            Matchers.is("123")
        );
    }

    @Test
    public void itShouldNotMergeExcludedProperty() {
        var student = new Student();
        student.setFirstName("Foo");
        student.setPasswordHash("123");

        var updater = new Student();
        updater.setLastName("Bar");

        BeanMerger.merge(student, updater, "lastName");

        MatcherAssert.assertThat(
            student.getLastName(),
            Matchers.nullValue()
        );
    }
}