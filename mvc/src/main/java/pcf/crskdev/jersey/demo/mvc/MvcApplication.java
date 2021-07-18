package pcf.crskdev.jersey.demo.mvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {
    "pcf.crskdev.jersey.demo"
})
public class MvcApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MvcApplication.class)
            .properties()
            .properties(
                "server.servlet.context-path=/api",
                "server.port=8081"
            )
            .build()
            .run(args);
    }
}
