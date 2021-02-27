package com.epam.esm.controller.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * The type Jwt token provider. Provides methods to work with Jason Web Tokens
 */
@Component
@PropertySource("classpath:security.properties")
public class JwtTokenProvider {

    /**
     * Secret key, is used in token signing
     */
    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * Number of milliseconds from current moment, until token will be valid
     */
    @Value("${jwt.validityInMillis}")
    private long validityInMillis;

    /**
     * Request header name, where JWT token is stored
     */
    @Value("${jwt.header}")
    private String authorizationHeader;

    private UserDetailsService userDetailsService;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Sets user details service.
     *
     * @param userDetailsService the user details service
     */
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Method creates token from userName.
     *
     * @param userName the user name
     * @return the string
     */
    public String createToken(String userName) {
        Claims claims = Jwts.claims().setSubject(userName);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Method validates token.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    /**
     * Method gets Authentication object from token.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(getUserName(token));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                securityUser.getUsername(),
                securityUser.getPassword(),
                securityUser.getAuthorities()
        );
        auth.setDetails(new AuthenticationDetails(securityUser.getAuthenticationDetails().getUserId(),
                securityUser.getAuthenticationDetails().isAdmin()));
        return auth;
    }

    /**
     * Method extracts user name from token.
     *
     * @param token the token
     * @return the user name
     */
    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Methods extracts token from http servlet request.
     *
     * @param request the request
     * @return the string
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
