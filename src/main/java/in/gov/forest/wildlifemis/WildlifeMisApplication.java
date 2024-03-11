package in.gov.forest.wildlifemis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication
//@EnableJpaAuditing
public class WildlifeMisApplication {
	public static void main(String[] args) {
		SpringApplication.run(WildlifeMisApplication.class, args);
	}

}
