package com.assessment.Attendance.config;

import org.springframework.context.annotation.Bean;

public class LayoutDialect {

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }
}
