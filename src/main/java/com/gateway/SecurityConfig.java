package com.gateway;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeExchange(exchanges -> exchanges
	            .pathMatchers("/login", "/public/**", "/patients/**").permitAll()  // Permet l'accès à /patients/** sans authentification
	            .anyExchange().authenticated()
	        )
	        .formLogin()
	        .and()
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessHandler((webFilterExchange, authentication) -> {
	                webFilterExchange.getExchange().getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
	                webFilterExchange.getExchange().getResponse().getHeaders().setLocation(java.net.URI.create("/login?logout"));
	                return webFilterExchange.getExchange().getResponse().setComplete();
	            })
	        );
	    return http.build();
	}

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
 