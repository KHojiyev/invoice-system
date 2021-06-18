package uzdeveloper.invoicesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Ahmad").password(passwordEncoder().encode("asd123")).roles("USER")
                .and()
                .withUser("Bobur").password(passwordEncoder().encode("asd123")).roles("ADMIN")
                .and()
                .withUser("Dilshod").password(passwordEncoder().encode("asd123")).roles("MANAGER")
                .and()
                .withUser("Javohir").password(passwordEncoder().encode("asd123")).roles("DIRECTOR");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()    // there may be add antMatcher() to control users with their roles or other
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }


}
