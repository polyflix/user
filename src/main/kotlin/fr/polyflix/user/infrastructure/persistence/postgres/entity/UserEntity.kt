package fr.polyflix.user.infrastructure.persistence.postgres.entity

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id val id: UUID,
    @Column val email: String,
    @Column val firstName: String,
    @Column val lastName: String,
    @Column val username: String,
    @Column val avatar: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: Collection<RoleEntity>
)