package fiveguys.edunet.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/error/**","/", "/main","/login", "/signup", "/css/**", "/js/**",
                        "/image/**","/student/login","/teacher/login","/student/detail","/idfind","/password").permitAll()
                        .requestMatchers("/subject").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated());
        http.sessionManagement((session)->session
            .sessionFixation().changeSessionId()
        );

        http.formLogin((login)->login
            .disable()
        );
        http.logout((logout)->logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/main")
            .invalidateHttpSession(true)
            .permitAll()
        );
        http.csrf((csrf)->csrf
            .disable()
        );
        return http.build();
    }
}
