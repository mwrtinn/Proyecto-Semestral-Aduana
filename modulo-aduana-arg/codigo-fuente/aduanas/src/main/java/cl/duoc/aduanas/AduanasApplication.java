package cl.duoc.aduanas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AduanasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AduanasApplication.class, args);
	}

}
