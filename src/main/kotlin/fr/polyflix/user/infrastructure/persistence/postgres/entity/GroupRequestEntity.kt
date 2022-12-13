package fr.polyflix.user.infrastructure.persistence.postgres.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "groups_requests")
class GroupRequestEntity(
    @Id val id: UUID,

    @Column
    val reason: String,

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    var createdDate: LocalDateTime
)