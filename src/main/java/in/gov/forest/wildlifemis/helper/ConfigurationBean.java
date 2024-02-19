package in.gov.forest.wildlifemis.helper;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationBean {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public JsonMapper jsonMapper(){
        return new JsonMapper();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
