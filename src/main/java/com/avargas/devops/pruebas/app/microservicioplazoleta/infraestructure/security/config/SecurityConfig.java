package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.config;

import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.security.auth.JwtValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.ApplicationContext;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final GenericHttpClient genericHttpClient;

    private static final String[] WHITE_LIST_URL = { "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/public/**"};

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Profile("!test")
    @Bean
    JwtValidationFilter jwtValidationFilter() throws  Exception {
        return new JwtValidationFilter(authenticationManager(), genericHttpClient);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ApplicationContext context) throws Exception {
        HttpSecurity security = http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URL).permitAll()  // Ajusta según tus rutas públicas
                        .anyRequest().authenticated()
                )
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Solo agrega el filtro si está registrado (en perfil !test)
        if (context.containsBean("jwtValidationFilter")) {
            JwtValidationFilter filter = context.getBean(JwtValidationFilter.class);
            security.addFilter(filter);
        }

        return security.build();
    }


}
