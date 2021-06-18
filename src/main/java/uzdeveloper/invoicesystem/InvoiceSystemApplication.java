package uzdeveloper.invoicesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"uzdeveloper.invoicesystem.controller"})
public class InvoiceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceSystemApplication.class, args);
    }

}
