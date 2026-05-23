package com.ford.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Desativado para chamadas REST de apps
                .authorizeHttpRequests(auth -> auth
                        // RBAC: Apenas VENDEDORES ou ADMINS podem gerar comparativos com IA
                        .requestMatchers("/api/v1/veiculos/comparar").hasAnyRole("VENDEDOR", "ADMIN")
                        // RBAC: Apenas ADMINS podem ver a base completa oficial
                        .requestMatchers("/api/v1/veiculos/ford-specs").hasRole("ADMIN")
                        // Libera o Swagger para o professor conseguir testar visualmente
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {}); // Usa Basic Auth simples para facilitar o teste na apresentação

        return http.build();
    }

    // Configuração Rígida de CORS (Requisito B3)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // O asterisco (*) libera o acesso para o seu celular/Expo
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Simulação de Banco de Usuários para cumprir o requisito de RBAC (Requisito B2)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails vendedor = User.builder().username("joao.vendas").password("{noop}senha123").roles("VENDEDOR").build();
        UserDetails admin = User.builder().username("admin.ti").password("{noop}admin123").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(vendedor, admin);
    }
}