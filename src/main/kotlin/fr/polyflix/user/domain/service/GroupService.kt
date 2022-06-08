package fr.polyflix.user.domain.service

import fr.polyflix.user.domain.authentication.AuthenticatedUser
import fr.polyflix.user.domain.entity.Group
import fr.polyflix.user.domain.props.CreateGroupProps
import fr.polyflix.user.domain.props.UpdateGroupProps
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface GroupService {
    fun getAllPaginated(pageable: Pageable): Page<Group>
    fun getOneBySlug(slug: String): Optional<Group>
    fun getOneById(id: UUID): Optional<Group>
    fun create(props: CreateGroupProps): Group
    fun update(id: UUID, props: UpdateGroupProps, user: AuthenticatedUser): Optional<Group>
    fun delete(id: UUID, user: AuthenticatedUser): Optional<Group>
}