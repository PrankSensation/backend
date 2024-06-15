package app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    @Value("${security.salt.length}")
    private int saltLength;

    @Value("${security.hash.length}")
    private int hashLength;

    @Value("${security.parallelism}")
    private int parallelism;

    @Value("${security.memory}")
    private int memory;

    @Value("${security.iterations}")
    private int iterations;

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}
