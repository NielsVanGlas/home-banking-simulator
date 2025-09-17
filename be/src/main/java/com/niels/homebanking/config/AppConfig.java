package com.niels.homebanking.config;

import com.niels.homebanking.repository.UserAccountRepository;
import com.niels.homebanking.util.Encryptor;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableAsync
@Configuration
public class AppConfig {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.symmetric.salt}")
    private String SALT;
    @Value("${app.symmetric.secret}")
    private String SECRET;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ApplicationRunner initializer(UserAccountRepository userAccountRepository) {
        return (args) -> {
            Encryptor cypher=new Encryptor(SECRET,SALT);
        };
    }
}

