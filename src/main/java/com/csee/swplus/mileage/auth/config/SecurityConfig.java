package com.csee.swplus.mileage.auth.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import com.csee.swplus.mileage.auth.filter.ExceptionHandlerFilter;
import com.csee.swplus.mileage.auth.filter.JwtTokenFilter;
import com.csee.swplus.mileage.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import com.csee.swplus.mileage.auth.util.JwtUtil;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final AuthService authService;

    @Value("${custom.host.client-walab}")
    private String client_walab;

    @Value("${custom.host.client-local}")
    private String client_local;

    @Value("${custom.jwt.secret}")
    private String SECRET_KEY;

    @PostConstruct
    public void init() {
        log.info("🚀 Allowed CORS Clients: {} {}", client_walab, client_local);
//        log.info("🔑 Loaded SECRET_KEY: {}", SECRET_KEY);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Key key = JwtUtil.getSigningKey(SECRET_KEY);
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/mileage/auth/**", "/api/mileage/share_profile/**").permitAll()
                        .antMatchers("/api/mileage/users/**",
                                "/api/mileage/{studentId}/search",
                                "/api/mileage/apply/{studentId}",
                                "/api/mileage/capability/**",
                                "/api/mileage/etc/**").authenticated()
                )
                .addFilterBefore(new ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenFilter(authService, key), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add(client_local);
        allowedOrigins.add(client_walab);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PATCH", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        log.info("🌍 CORS Allowed Origins: {} {}", client_walab.toString(), client_local.toString());
        log.info("✅ CORS Allowed Methods: {}", config.getAllowedMethods());

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**", config);
        return source;
    }
}