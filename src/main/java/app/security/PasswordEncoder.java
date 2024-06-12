package app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordEncoder extends Argon2PasswordEncoder{


    public PasswordEncoder(int saltLength, int hashLength, int parallelism, int memory, int iterations) {
        super(saltLength, hashLength, parallelism, memory, iterations);

    }

    public PasswordEncoder() {
        super(System.getenv("SALT_LENGTH") != null ? Integer.parseInt(System.getenv("SALT_LENGTH")) : 32 , System.getenv("HASH_LENGTH") != null ? Integer.parseInt(System.getenv("HASH_LENGTH")) : 32 ,System.getenv("PARALLELISM") != null ? Integer.parseInt(System.getenv("PARALLELISM")) : 2 ,System.getenv("MEMORY") != null ? Integer.parseInt(System.getenv("MEMORY")) : 200 ,System.getenv("ITERATIONS") != null ? Integer.parseInt(System.getenv("ITERATIONS")) : 2 );
    }
}
