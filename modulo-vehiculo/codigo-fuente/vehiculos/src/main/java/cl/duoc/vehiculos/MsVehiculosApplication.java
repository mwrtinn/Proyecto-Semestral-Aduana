package cl.duoc.vehiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVehiculosApplication.class, args);
	}

}
