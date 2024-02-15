package in.gov.forest.wildlifemis.config;


import in.gov.forest.wildlifemis.credential.jwt.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class FilterConfig{
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("static/uploads/**")
//                .addResourceLocations("classpath:static/uploads/");
//    }
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> myFilterRegistration() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Set filter order
        return registrationBean;
    }
}
