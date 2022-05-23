package fr.polyflix.user.domain.messaging.producer

import fr.polyflix.user.domain.messaging.event.UserEvent

interface UserProducer {
    fun send(event: UserEvent)
}