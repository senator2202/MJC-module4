package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Password encoder configuration.
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * The constant ENCRYPTION_STRENGTH.
     */
    private static final int ENCRYPTION_STRENGTH = 12;

    /**
     * Password encoder bean.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPTION_STRENGTH);
    }
}
