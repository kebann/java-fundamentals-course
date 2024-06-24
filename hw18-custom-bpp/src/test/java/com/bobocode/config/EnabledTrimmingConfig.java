package com.bobocode.config;

import com.trimmer.annotation.EnableStringTrimming;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.bobocode.service")
@EnableStringTrimming
public class EnabledTrimmingConfig {
}