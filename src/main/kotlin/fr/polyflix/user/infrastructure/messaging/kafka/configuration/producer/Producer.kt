package fr.polyflix.user.infrastructure.messaging.kafka.configuration.producer

data class Producer(
    val keySerializer: String,
    val valueSerializer: String
)