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
                // 1. Conecta la configuración CORS de abajo con Spring Security
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Console H2 (solo por si se necesita en local)
                        .requestMatchers("/h2-console/**").permitAll()

                        // 2. CORRECCIÓN DEL 403: Para permitir las rutas con y sin el "/api"
                        .requestMatchers("/api/auth/**", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/bicicletas/**", "/bicicletas/**").permitAll()

                        // Endpoints protegidos (solo ADMIN puede subir fotos y manejar el panel)
                        .requestMatchers("/api/media/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Todito lo demás requiere estar logueado
                        .anyRequest().authenticated()
                )
                // Necesario para que H2 console funcione si se requiere
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 3. CORRECCIÓN VERCEL: Permite cualquier origen (localhost, Vercel, etc.)
        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        // Exponemos las cabeceras para que el frontend pueda leer el PDF y el JWT
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

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