package cl.duoc.menores.config;
 
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class SwaggerConfig {
 
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Menores Service")
                .version("1.0.0")
                .description("Microservicio para el control y gestión de menores de edad en el sistema de aduanas."));
    }
}