package com.dvaletin.demo.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "demo")
class DemoConfigurationProperties {
    lateinit var serviceAccountJsonPath: String
    lateinit var databaseUrl: String
}

@Configuration
@EnableConfigurationProperties(DemoConfigurationProperties::class)
class DemoConfiguration