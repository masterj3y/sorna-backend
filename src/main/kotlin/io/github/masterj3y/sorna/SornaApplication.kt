package io.github.masterj3y.sorna

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class SornaApplication

fun main(args: Array<String>) {
    runApplication<SornaApplication>(*args)
}
