package io.github.masterj3y.sorna

import io.github.masterj3y.sorna.filestorage.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Bootstrap @Autowired constructor(private val fileStorageService: FileStorageService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        initFileStorage()
    }

    fun initFileStorage() = fileStorageService.init()
}