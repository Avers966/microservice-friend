package ru.skillbox.diplom.group35.microservice.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import ru.skillbox.diplom.group35.library.core.annotation.EnableBaseRepository;
import ru.skillbox.diplom.group35.library.core.annotation.EnableOpenFeign;
import ru.skillbox.diplom.group35.library.core.annotation.EnableSecurity;
import ru.skillbox.diplom.group35.library.core.annotation.JwtProvider;

/**
 * Application
 *
 * @author Grigory Dyakonov
 */
@JwtProvider
@EnableSecurity
@EnableOpenFeign
@EnableBaseRepository
@SpringBootApplication
//        (exclude = { SecurityAutoConfiguration.class,
//        ManagementWebSecurityAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
