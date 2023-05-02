package com.yournet.yournet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YournetApplication

fun main(args: Array<String>) {
    runApplication<YournetApplication>(*args)
}
