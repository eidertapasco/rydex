package com.bikeshop.rydex.config;

import com.bikeshop.rydex.repository.ClienteRepository;
import com.bikeshop.rydex.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ClienteRepository clienteRepository;

    // Le dice a Spring Security que busque usuarios en la BD, no en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> clienteRepository.findByEmail(email)
                .map(c -> new User(
                        c.getEmail(),
                        c.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + c.getRol().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // H2 Console (solo desarrollo)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Auth endpoints — públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        // Bicicletas GET — público (la galería Angular no requiere login)
                        .requestMatchers(HttpMethod.GET, "/api/bicicletas/**").permitAll()

                        // Permite a cualquiera ver las imágenes subidas
                        .requestMatchers("/uploads/**").permitAll()
                        // Permite solo al administrador subir imágenes
                        .requestMatchers("/api/media/**").hasRole("ADMIN")

                        // Admin endpoints — solo ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                // Necesario para que H2 console funcione en iframe
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Permite el frontend Angular en desarrollo
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}