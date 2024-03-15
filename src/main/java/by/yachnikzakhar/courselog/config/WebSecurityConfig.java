package by.yachnikzakhar.courselog.config;


import by.yachnikzakhar.courselog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;



@Configuration
@EnableWebSecurity
@ComponentScans(value = {
        @ComponentScan("by.yachnikzakhar.courselog.config")
})
public class WebSecurityConfig{

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration", "/css/**", "/images/**", "/script/**").permitAll()
                        .requestMatchers("/student/**").hasAuthority("STUDENT")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/educator/**").hasAuthority("EDUCATOR")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProcessing")
                        .defaultSuccessUrl("/redirectUserMainPage")
                        .permitAll()
                )
                .logout(lgtm -> lgtm
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
