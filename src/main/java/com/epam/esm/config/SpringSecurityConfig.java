package com.epam.esm.config;

import com.epam.esm.controller.security.JwtTokenFilter;
import com.epam.esm.controller.security.PersonalDataFilter;
import com.epam.esm.service.impl.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

/**
 * The type Spring security config.
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;
    private final PersonalDataFilter personalDataFilter;
    private final CustomOidcUserService oidcUserService;

    /**
     * Instantiates a new Spring security config.
     *
     * @param jwtTokenFilter     the jwt token filter
     * @param personalDataFilter the personal data filter
     * @param oidcUserService    the open id connect user service
     */
    @Autowired
    public SpringSecurityConfig(JwtTokenFilter jwtTokenFilter,
                                PersonalDataFilter personalDataFilter,
                                CustomOidcUserService oidcUserService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.personalDataFilter = personalDataFilter;
        this.oidcUserService = oidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/login", "/api/auth/register").anonymous()
                .antMatchers(HttpMethod.GET, "/api/certificates/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterAfter(personalDataFilter, JwtTokenFilter.class)
                .oauth2Login()
                .userInfoEndpoint().oidcUserService(oidcUserService);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
