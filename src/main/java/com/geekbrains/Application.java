package com.geekbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// TODO:
// 1. Покрыть 30%-50% кода автотестами


//TODO: для себя
//1. почему падает npe на User user = userService.findByUsername(((CustomPrincipal)authentication.getPrincipal()).getUsername()); saveOrder


