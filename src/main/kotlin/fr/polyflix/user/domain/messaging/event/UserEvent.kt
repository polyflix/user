package fr.polyflix.user.domain.messaging.event

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.messaging.model.UserMessage
import java.util.*

data class UserEvent private constructor(val trigger: Trigger?, val id: UUID?, val fields: UserMessage?) {
    data class Builder(
        private var id: UUID? = null,
        private var trigger: Trigger? = null,
        private var fields: UserMessage? = null
    ) {
        fun withTrigger(trigger: Trigger) = apply { this.trigger = trigger }
        fun withId(id: UUID) = apply { this.id = id }
        fun withUser(user: User) = apply {
            this.fields = UserMessage(user.id, user.email, user.username, user.firstName, user.lastName, user.avatar)
            this.id = user.id
        }
        fun build(): UserEvent = UserEvent(trigger, id, fields)
    }
}