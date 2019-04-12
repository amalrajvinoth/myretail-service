package in.amal.retail;

import in.amal.retail.exception.GlobalExceptionHandler;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, GlobalExceptionHandler.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
