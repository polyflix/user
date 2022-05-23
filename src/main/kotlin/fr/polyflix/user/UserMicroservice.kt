package fr.polyflix.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class UserMicroservice

fun main(args: Array<String>) {
	runApplication<UserMicroservice>(*args)
}
