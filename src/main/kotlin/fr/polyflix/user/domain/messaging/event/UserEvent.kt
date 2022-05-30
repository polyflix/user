package fr.polyflix.user.domain.messaging.event

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.messaging.model.UserMessage
import java.util.UUID

data class UserEvent private constructor(val trigger: Trigger?, val payload: UserMessage?) {
    data class Builder(
        private var trigger: Trigger? = null,
        private var payload: UserMessage? = null
    ) {
        fun withTrigger(trigger: Trigger) = apply { this.trigger = trigger }
        fun withId(id: UUID) = apply { this.payload = UserMessage(id) }
        fun withUser(user: User) = apply {
            this.payload = UserMessage(user.id, user.email, user.username, user.firstName, user.lastName, user.avatar, user.roles.map { it.name })
        }
        fun build(): UserEvent = UserEvent(trigger, payload)
    }
}