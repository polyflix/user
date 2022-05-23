package fr.polyflix.user.infrastructure.messaging.kafka.consumer

import fr.polyflix.user.domain.service.UserService
import fr.polyflix.user.infrastructure.authentication.keycloak.event.KeycloakEventType
import fr.polyflix.user.infrastructure.authentication.keycloak.event.KeycloakUserEvent
import fr.polyflix.user.infrastructure.authentication.keycloak.mapper.KeycloakUserMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaKeycloakConsumer(private val userService: UserService, private val keycloakUserMapper: KeycloakUserMapper) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${spring.kafka.consumer.keycloak.topic}"], containerFactory = "kafkaKeycloakContainerFactory")
    fun onMessage(event: KeycloakUserEvent, ack: Acknowledgment) {
        logger.info("Received new event: $event")
        when (event.trigger) {
            KeycloakEventType.CREATE -> {
                userService.createUser(keycloakUserMapper.toDomain(event.data))
                ack.acknowledge()
            }
        }
    }
}
