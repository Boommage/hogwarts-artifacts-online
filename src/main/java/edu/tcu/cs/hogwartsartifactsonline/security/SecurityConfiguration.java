package edu.tcu.cs.hogwartsartifactsonline.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, this.baseUrl+"/artifacts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, this.baseUrl + "/users/**").hasAuthority("ROLE_admin") //Protecting the endpoint                        .requestMatchers(HttpMethod.GET, this.baseUrl + "/users/**").hasAuthority("ROLE admin") //Protecting the endpoint
                        .requestMatchers(HttpMethod.POST, this.baseUrl + "/users").hasAuthority("ROLE_admin") //Protecting the endpoint
                        .requestMatchers(HttpMethod.PUT, this.baseUrl + "/users/**").hasAuthority("ROLE_admin") //Protecting the endpoint
                        .requestMatchers(HttpMethod.DELETE, this.baseUrl + "/users/**").hasAuthority("ROLE_admin") //Protecting the endpoint
                        .requestMatchers("/h2-console/**").permitAll()
                        //Disalow everything else - must be authenticated
                        .anyRequest().authenticated() //Allways a good idea to put this last
                )
                .headers(headers -> headers.frameOptions(Customizer.withDefaults())) //for h2 browser console access
                .csrf(csrf -> csrf.disable()) //cross site request forgery -
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
