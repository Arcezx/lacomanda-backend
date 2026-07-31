package com.lacomanda.backend.config;
import com.lacomanda.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // --- Público, sin token ---
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/imagenes/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/categorias/**",
                                "/api/productos/**",
                                "/api/mesas/codigo/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pedidos").permitAll()

                        //Requiere token, cualquier rol (ADMIN o CAMARERO)
                        .requestMatchers(HttpMethod.GET, "/api/pedidos/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/pedidos/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/mesas").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/mesas/**").authenticated()

                        //Solo ADMIN: gestión de carta
                        .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/alergenos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/alergenos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/alergenos/**").hasRole("ADMIN")

                        //Solo ADMIN: gestión de mesas (crear/editar/borrar)
                        .requestMatchers(HttpMethod.POST, "/api/mesas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/mesas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/mesas/**").hasRole("ADMIN")

                        //Solo ADMIN: gestión de usuarios
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        //Cualquier otra cosa: requiere estar logueado, sin más restricción
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8100", "http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}