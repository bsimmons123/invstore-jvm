package com.invstore.invstorejvm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class InvstoreJvmApplication

fun main(args: Array<String>) {
    runApplication<InvstoreJvmApplication>(*args)
}
