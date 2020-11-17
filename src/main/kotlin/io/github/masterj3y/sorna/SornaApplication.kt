package io.github.masterj3y.sorna

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SornaApplication

fun main(args: Array<String>) {
    runApplication<SornaApplication>(*args)
}
