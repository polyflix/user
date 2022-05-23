package fr.polyflix.user.infrastructure.messaging.kafka.configuration.consumer

data class Consumer(
    val autoOffsetReset: String,
    val keyDeserializer: String,
    val valueDeserializer: String,
    val keycloak: ConsumerProps
)