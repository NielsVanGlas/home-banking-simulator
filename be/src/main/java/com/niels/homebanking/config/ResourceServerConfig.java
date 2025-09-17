package com.niels.homebanking.config;

import com.niels.homebanking.config.auth.RequestFilter;
import com.niels.homebanking.config.auth.SerializableAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class ResourceServerConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SerializableAuthenticationEntryPoint serializableAuthenticationEntryPoint;

    @Autowired
    private RequestFilter requestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/bank/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/currency/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/currency").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/currency/**").hasAnyRole("ADMIN")
                                .requestMatchers("/transaction/**").permitAll()
                                .requestMatchers("/status/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/user/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/user/**").permitAll()

                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(serializableAuthenticationEntryPoint));
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Profile({"dev"})
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("X-Get-Header", "content-disposition"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
