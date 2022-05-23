package fr.polyflix.user.infrastructure.configuration

import fr.polyflix.user.domain.messaging.producer.UserProducer
import fr.polyflix.user.domain.persistence.repository.UserRepository
import fr.polyflix.user.domain.service.UserService
import fr.polyflix.user.domain.service.impl.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun userService(userRepository: UserRepository, userProducer: UserProducer) : UserService {
        return UserServiceImpl(userRepository, userProducer)
    }
}