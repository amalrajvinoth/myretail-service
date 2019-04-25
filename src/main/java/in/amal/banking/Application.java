package in.amal.banking;

import in.amal.banking.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Application.class, GlobalExceptionHandler.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
