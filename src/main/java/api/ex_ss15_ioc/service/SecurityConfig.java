package api.ex_ss15_ioc.service;

import api.ex_ss15_ioc.repository.UserRepository;
import api.ex_ss15_ioc.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, userDetailsService());

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Endpoints công khai đăng ký/đăng nhập
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. GET sản phẩm công khai cho mọi user/khách hàng[cite: 2]
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() //[cite: 2]

                        // 3. POST, PUT, DELETE sản phẩm yêu cầu quyền ROLE_ADMIN hoặc ROLE_STAFF[cite: 2]
                        .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF") //[cite: 2]
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF") //[cite: 2]
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STAFF") //[cite: 2]

                        // Các request khác bắt buộc phải xác thực
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
