package pcf.crskdev.jersey.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;
import pcf.crskdev.jersey.demo.common.input.StudentInput;
import pcf.crskdev.jersey.demo.common.services.StudentService;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = "pcf.crskdev.jersey.demo.common.enitities")
public final class StartupConfiguration {

    @Autowired
    StudentService studentService;

    @Bean
    ApplicationRunner applicationRunner(TransactionTemplate transactionTemplate) {
        return (args) -> {
            transactionTemplate.executeWithoutResult((status) -> {
                var student = new StudentInput("user", "john", "doe",
                    "123"
                );
                studentService.register(student);
            });
        };
    }
}
