package fr.polyflix.user.domain.messaging.event

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.messaging.model.UserMessage

data class UserEvent private constructor(val trigger: Trigger?, val fields: UserMessage?) {
    data class Builder(
        private var trigger: Trigger? = null,
        private var payload: UserMessage? = null
    ) {
        fun withTrigger(trigger: Trigger) = apply { this.trigger = trigger }
        fun withUser(user: User) = apply {
            this.payload = UserMessage(user.id, user.email, user.username, user.firstName, user.lastName, user.avatar)
        }
        fun build(): UserEvent = UserEvent(trigger, payload)
    }
}