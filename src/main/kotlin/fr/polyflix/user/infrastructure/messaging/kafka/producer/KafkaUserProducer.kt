package fr.polyflix.user.infrastructure.messaging.kafka.producer

import fr.polyflix.user.domain.messaging.event.UserEvent
import fr.polyflix.user.domain.messaging.producer.UserProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaUserProducer(
    @Value("\${spring.kafka.producer.topic}")
    private val topic: String,
    private val kafkaTemplate: KafkaTemplate<String, UserEvent>
) : UserProducer {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun send(event: UserEvent) {
        try {
            logger.info("Building record to send event: $event")
            val producerRecord = ProducerRecord<String, UserEvent>(topic, event)
            kafkaTemplate.send(producerRecord)
            logger.info("The message has been sent to the $topic topic")
        } catch (e: KafkaException) {
            logger.error("Failed to send message on the topic $topic. Details: ${e.message}")
        }
    }
}