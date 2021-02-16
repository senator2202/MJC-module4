package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/tags/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/certificates/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/certificates/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users/**").anonymous()
                .and()
                .httpBasic();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("user")
                .passwordEncoder(passwordEncoder()::encode)
                .roles("USER")
                .build();
        UserDetails adminDetails = User.builder()
                .username("admin")
                .password("admin")
                .passwordEncoder(passwordEncoder()::encode)
                .roles("ADMIN", "USER")
                .build();
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if (manager.userExists(userDetails.getUsername())) {
            manager.deleteUser(userDetails.getUsername());
        }
        if (manager.userExists(adminDetails.getUsername())) {
            manager.deleteUser(adminDetails.getUsername());
        }
        manager.createUser(userDetails);
        manager.createUser(adminDetails);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
