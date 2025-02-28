package com.example.config;

import com.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return  httpSecurity.build();
    }


    // hard coded UserDetailsService
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("Rahul")
//                .password("rahul@123")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User
//                .withDefaultPasswordEncoder()
//                .username("Admin")
//                .password("admin@123")
//                .roles("ADMIN")
//                .build();
//
//        return  new InMemoryUserDetailsManager(user, admin);
//    }
}
