package fr.polyflix.user.infrastructure.messaging.kafka.configuration

import fr.polyflix.user.domain.messaging.event.UserEvent
import fr.polyflix.user.infrastructure.authentication.keycloak.event.KeycloakUserEvent
import fr.polyflix.user.infrastructure.messaging.kafka.configuration.consumer.ConsumerProps
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConfiguration(private val kafkaProperties: KafkaConfigurationProperties) {

    @Bean
    fun kafkaKeycloakContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KeycloakUserEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KeycloakUserEvent>()

        // Set the concurrency of the consumer
        factory.setConcurrency(kafkaProperties.consumer.keycloak.concurrency)
        factory.setCommonErrorHandler(KafkaConsumerErrorHandler())

        factory.consumerFactory = createConsumerFactory<KeycloakUserEvent>(kafkaProperties.consumer.keycloak)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.containerProperties.isSyncCommits = true

        return factory
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, UserEvent> {
        return KafkaTemplate(DefaultKafkaProducerFactory(configureProducer()))
    }

    private inline fun <reified T> createConsumerFactory(consumerProps: ConsumerProps): ConsumerFactory<String, T> {
        val props: MutableMap<String, Any> = HashMap()

        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        props[ConsumerConfig.GROUP_ID_CONFIG] = consumerProps.groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java

        return DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            ErrorHandlingDeserializer(JsonDeserializer(T::class.java, false))
        )
    }

    private fun configureProducer(): Map<String, Any> {
        var configProps = mutableMapOf<String, Any>();
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = kafkaProperties.producer.keySerializer
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = kafkaProperties.producer.valueSerializer
        return configProps
    }
}
