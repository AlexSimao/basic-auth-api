package com.estudo.springsecurity.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita para essa classe as configuraçoes do Spring Security
@EnableMethodSecurity(securedEnabled = true) // Habilita a segurança baseada em anotações @PreAuthorize e @Secured
public class SecurityConfig {

  @SuppressWarnings("unused")
  @Autowired
  // Mesmo que não seja utilizado, é necessário para o Spring Security
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Desabilita a proteção CSRF
        .csrf(csrf -> csrf.disable())
        // Configura a política de sessão como stateless
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            // So deve ser descomentado para cadastro do primeiro usuário ADMIN no sistema.
            // .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .anyRequest().authenticated())
        // Verifica se o token do usuario é válido para operação solicitada
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
