package fr.polyflix.user.infrastructure.persistence.postgres.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "groups")
class GroupEntity(
    @Id
    val id: UUID,

    @Column
    val name: String,

    @Column(unique = true)
    val slug: String,

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    val owner: UserEntity,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "groups_users",
        joinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    val members: Set<UserEntity>,
)