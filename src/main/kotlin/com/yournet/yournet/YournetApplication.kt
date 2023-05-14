package com.yournet.yournet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class YournetApplication

fun main(args: Array<String>) {
    runApplication<YournetApplication>(*args)
}
