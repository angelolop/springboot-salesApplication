package angelo.com.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SalesApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
