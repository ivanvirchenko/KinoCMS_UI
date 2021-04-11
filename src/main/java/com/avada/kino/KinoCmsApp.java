package com.avada.kino;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class KinoCmsApp {
    public static void main(String[] args) {
        SpringApplication.run(KinoCmsApp.class, args);
    }
}
