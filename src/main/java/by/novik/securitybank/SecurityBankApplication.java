package by.novik.securitybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityBankApplication {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityBankApplication.class, args);
    }

}
