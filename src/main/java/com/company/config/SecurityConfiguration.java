package com.company.config;

import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Spring Configuration Class
@EnableWebSecurity // Marker annotation, allows Spring to find its web configuration
// WebSecurityConfigurerAdapter provides custom configuration methods
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    // Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Integrate SpringDataJpa and Hibernate with SpringSecurity
    @Bean // AuthenticationProvider implementation that uses a UserDetailsService and PasswordEncoder to authenticate a username and password
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    // Pass AuthenticationProvider to the config method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Providing access to some URLs
        http
                // Authorized Requests
                .authorizeRequests().antMatchers(
                        "/registration**",
                        "/js/**", "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()

                .and()

                // FormLogin/LoginPage
                .formLogin().loginPage("/login") // Configure custom login page
                .permitAll()

                .and()

                // Logout
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout") // Navigate to Login page after logout
                .permitAll();
    }
}
