package com.demo.config;

import com.demo.utils.JwtTokenRolesConverter;
import com.demo.utils.OpaqueTokenRolesConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
    private String introspectUri;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String clientSecret;
    */

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // JWT Support
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtTokenRolesConverter());
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // JWT Support
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)))
                // Opaque Token Support
                /*
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueTokenConfigurer -> opaqueTokenConfigurer
                                .authenticationConverter(new OpaqueTokenRolesConverter())
                                .introspectionUri(introspectUri)
                                .introspectionClientCredentials(clientId, clientSecret))) */
                .build();
    }

}
