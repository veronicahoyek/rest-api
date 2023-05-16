package edu.ua.oop1veronicahoyek.auth.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // to be able to authorize by Role in the controller instead of using request matchers
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    //Since we Enabled Web Security, security will look for a bean called SecurityFilterChain
    //This chain is responsible for configuring all security of our application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // disable csrf
                .authorizeHttpRequests()
                .requestMatchers(
                        "/authentication/**",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                )
                .permitAll() // we are whitelisting all requests (or request patterns) before that line
                .anyRequest().authenticated() // we are securing all the other requests
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // to create a new session for each request
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Execute our JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
        return http.build();
    }
}
