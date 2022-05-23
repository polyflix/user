package fr.polyflix.user.infrastructure.messaging.kafka.configuration

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.kafka.listener.MessageListenerContainer

class KafkaConsumerErrorHandler: CommonErrorHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handleRecord(
        thrownException: Exception,
        record: ConsumerRecord<*, *>,
        consumer: Consumer<*, *>,
        container: MessageListenerContainer
    ) {
        when (thrownException) {
            is ListenerExecutionFailedException -> {
                logger.error("Failed to deserialize payload received from topic=${record.topic()}. Details = ${thrownException.message}")
                logger.warn("Committing messages with failure to prevent re-polling from other consumers")
                consumer.commitSync()
            }
            else -> logger.error("Unhandled exception: ${thrownException.javaClass}. Details = ${thrownException.message}")
        }
    }
}
