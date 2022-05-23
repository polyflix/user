package fr.polyflix.user.infrastructure.messaging.kafka.configuration.consumer

data class ConsumerProps(
    val groupId: String,
    val topic: String,
    val concurrency: Int = 1
)
