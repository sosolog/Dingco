package com.dingco.pedal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedalApplication /*extends SpringBootServletInitializer */{

    public static void main(String[] args) {
        SpringApplication.run(PedalApplication.class, args);
    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(PedalApplication.class);
//    }

}
