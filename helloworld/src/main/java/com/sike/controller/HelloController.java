package com.sike.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    public class HelloController {
        @RequestMapping("/hello")
        public String hello(){
            return "<h1>Hello World 2023309274 陈航欣</h1>";
        }
    }

