package fiveguys.edunet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/main","/login", "/signup", "/css/**", "/js/**","/image/**").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement((session)->session
            .sessionFixation().changeSessionId()
        );

        http.formLogin((login)->login
            .loginPage("/login")
            .loginProcessingUrl("/login") 
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/",false)
            .permitAll()
        );
        http.logout((logout)->logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .permitAll()
        );
        http.csrf((csrf)->csrf
            .disable()
        );
        return http.build();
    }
}
