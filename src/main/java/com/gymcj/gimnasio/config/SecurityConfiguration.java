package com.gymcj.gimnasio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gymcj.gimnasio.config.filter.JwtTokenValidator;
import com.gymcj.gimnasio.repository.UsuarioRepository;
import com.gymcj.gimnasio.util.JwtUtils;

@Configuration
public class SecurityConfiguration {

    private final JwtUtils jwtUtils;

    public SecurityConfiguration(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/styles/**", "/scripts/**", "/images/**").permitAll()
                        .requestMatchers("/", "/register", "/api/auth/**").permitAll() // Permitir acceso al endpoint de autenticación JWT
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/**").hasRole("USUARIO")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/error/403"))
                //.sessionManagement(session -> session
                        //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT no usa sesiones
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .defaultSuccessUrl("/usuario/home", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(validacionExitosa()) // Redirige según el rol después del login OAuth2
                )
                .formLogin(from -> from
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .successHandler(validacionExitosa()) // Redirige según el rol después del login normal
                )
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtTokenValidator jwtTokenValidator(JwtUtils jwtUtils) {
        return new JwtTokenValidator(jwtUtils);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return new CustomerUserDetailService(usuarioRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder))
                .build();
    }

    @Bean
    public SessionRegistry datosSession() {
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler validacionExitosa() {
        return (request, response, authentication) -> {
            
            String token = jwtUtils.crearToken(authentication);
    
            response.setHeader("Authorization", "Bearer " + token);
    
            // Redirigir según el rol
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            boolean isUsuario = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USUARIO"));
    
            if (isAdmin) {
                response.sendRedirect("/admin/home");
            } else if (isUsuario) {
                response.sendRedirect("/usuario/home");
            } else {
                response.sendRedirect("/");
            }
        };
    }
}