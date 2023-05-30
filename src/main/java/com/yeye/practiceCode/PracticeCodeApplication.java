package com.yeye.practiceCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PracticeCodeApplication {

  public static void main(String[] args) {
    SpringApplication.run(PracticeCodeApplication.class, args);
  }

}
