package in.gov.forest.wildlifemis;


//import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
//@OpenAPIDefinition(info= @Info(title = "WildlifeMIS API", version = "2.0", description="Wildlife MIS Project"))
//@OpenAPIDefinition
//@EnableJpaAuditing
public class WildlifeMisApplication {
	public static void main(String[] args) {
		SpringApplication.run(WildlifeMisApplication.class, args);
	}

}
