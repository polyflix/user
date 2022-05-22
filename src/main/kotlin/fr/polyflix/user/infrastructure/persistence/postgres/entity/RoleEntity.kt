package fr.polyflix.user.infrastructure.persistence.postgres.entity

import fr.polyflix.user.domain.enum.Roles
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id val id: UUID,

    @Enumerated(EnumType.STRING)
    @Column
    val name: Roles,
)