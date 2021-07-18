package pcf.crskdev.jersey.demo.jaxrs;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pcf.crskdev.jersey.demo.StartupConfiguration;
import pcf.crskdev.jersey.demo.common.services.StudentService;

import javax.ws.rs.ApplicationPath;

@SpringBootApplication(scanBasePackages = {"pcf.crskdev.jersey.demo"})
@ApplicationPath("api")
public class JaxRsApplication extends ResourceConfig {

    public JaxRsApplication() {
        super();
        packages("pcf.crskdev.jersey.demo.jaxrs.context");
    }

    public static void main(String[] args) {
        SpringApplication
            .run(JaxRsApplication.class, args);
    }

}
