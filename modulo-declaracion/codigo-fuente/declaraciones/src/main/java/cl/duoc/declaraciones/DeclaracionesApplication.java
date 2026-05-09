package cl.duoc.declaraciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DeclaracionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeclaracionesApplication.class, args);
	}

}
