package in.gov.forest.wildlifemis.config;//package com.wildlife.config;


import in.gov.forest.wildlifemis.credential.authentication.AppUserServiceImpl;
import in.gov.forest.wildlifemis.credential.jwt.AuthEntryPointJwt;
import in.gov.forest.wildlifemis.credential.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
@Configuration
@EnableSpringConfigured
@RequiredArgsConstructor
@EnableMethodSecurity// @EnableGlobalMethodSecurity deprecated
public class WebSecurityConfig { //extends WebSecurityConfigurerAdapter

    //Alt+Shift+Enter optimize import

    @Autowired
    private AppUserServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationFilter filter;
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    authz->
                            authz.requestMatchers("/public/login").permitAll()
                            .anyRequest().authenticated()
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                http.headers(headers ->
                headers.xssProtection(
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        cps -> cps.policyDirectives("script-src 'self' .....")
                ));
        return http.build();
    }

}

