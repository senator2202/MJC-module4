package com.epam.esm.config;

import com.epam.esm.controller.security.JwtTokenFilter;
import com.epam.esm.controller.security.PersonalDataFilter;
import com.epam.esm.model.dto.UserRegistrationDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Spring security config.
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ENCRYPTION_STRENGTH = 12;
    private final JwtTokenFilter jwtTokenFilter;
    private final PersonalDataFilter personalDataFilter;
    private UserService userService;
    private UserRepository userRepository;

    /**
     * Instantiates a new Spring security config.
     *
     * @param jwtTokenFilter     the jwt token filter
     * @param personalDataFilter the personal data filter
     */
    @Autowired
    public SpringSecurityConfig(JwtTokenFilter jwtTokenFilter, PersonalDataFilter personalDataFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.personalDataFilter = personalDataFilter;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(personalDataFilter, JwtTokenFilter.class);
    }

    @Bean
    public PrincipalExtractor principalExtractor() {
        return map -> {
            String email = (String) map.get("email");
            return userService.findByUsername(email).orElseGet(() -> {
                User user = new User();
                user.setUsername(email);
                user.setName((String) map.get("name"));
                user.setPassword("1234");
                return userService.add(user);
            });
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
