package Proyecto.Peliculas.Config;

import Proyecto.Peliculas.JWT.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableWebSecurity // Habilita la seguridad web
@EnableMethodSecurity
public class SegurityConfig {

                        // Inyectamos el filtro JWT que crearemos más adelante
                        private final JwtAuthenticationFilter  jwtAuthenticationFilter;

                        public SegurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
                            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                        }

                        // Exponer el AuthenticationManager (necesario para el login)
                        @Bean
                        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception{
                            return authenticationConfiguration.getAuthenticationManager();
                        }

                        // Define cómo se codificarán las contraseñas
                        @Bean
                        public PasswordEncoder passwordEncoder(){
                            return new BCryptPasswordEncoder();
                        }

                        @Bean
                        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{

                            http
                            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para APIs REST

                                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usar sesiones HTTP (usar JWT)
                                    .authorizeHttpRequests(auth -> auth
                                            .requestMatchers("/api/auth/**").permitAll()// Permite el acceso sin autenticación a la API de login/registro
                                            .anyRequest().authenticated()
                                    )

                             .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                                    return http.build();








    }


}
