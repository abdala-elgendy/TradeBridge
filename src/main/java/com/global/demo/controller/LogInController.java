package com.global.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LogInController {
    @RequestMapping("/login")
    public String login() {

        return "Welcome to the login page";
    }
}
