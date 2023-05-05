package com.vellmond.vellmondapi.controller;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vellmond")
public class VellmondController {

    private List<String> caracteres = new ArrayList<String>();
    Faker faker = new Faker();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            caracteres.add(faker.dragonBall().character());
        }
    }


    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Vellmond!";
    }

    @GetMapping("/dragonball")
    public List<String> dragonball() {
        return caracteres;
    }
}
