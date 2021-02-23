package com.epam.esm.config;

import com.epam.esm.controller.security.JwtTokenFilter;
import com.epam.esm.controller.security.PersonalDataFilter;
import com.epam.esm.controller.security.SecurityUser;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * The type Spring security config.
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableOAuth2Sso
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
                .antMatchers("/api/auth/login", "/api/auth/register").anonymous()
                .anyRequest().authenticated()/*
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(personalDataFilter, JwtTokenFilter.class)
                .oauth2Login()
                /*.and()
                .oauth2ResourceServer()
                .jwt()*/;
    }

    @Bean
    public PrincipalExtractor principalExtractor() {
        return map -> {
            Integer a =1;
            return a;
        };
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public JwtDecoder jwtDecoder() {
        byte[] signingKey = Base64.getEncoder().encode("AjW2G5tPm3zLuxEbu39KoECv".getBytes());
        return token -> {
            io.jsonwebtoken.Jws<Claims> jjwt = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token);
            Date issuedAt = jjwt.getBody().getIssuedAt();
            Date expiration= jjwt.getBody().getExpiration();
            JwsHeader<?> headers = jjwt.getHeader();
            Claims claims = jjwt.getBody();

            Jwt jwt = new Jwt(token, issuedAt.toInstant(), expiration.toInstant(), headers, claims);
            return jwt;
        };
    }

    Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractor() {
        return new GrantedAuthoritiesExtractor();
    }

    static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
        protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
            Collection<String> authorities = (Collection<String>)
                    jwt.getClaims().get("mycustomclaim");

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }*/
}
