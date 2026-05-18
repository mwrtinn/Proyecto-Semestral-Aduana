package cl.duoc.turno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TurnoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurnoApplication.class, args);
    }
}