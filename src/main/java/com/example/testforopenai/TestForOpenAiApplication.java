package com.example.testforopenai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.testforopenai.Mapper")
public class TestForOpenAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestForOpenAiApplication.class, args);
    }

}
