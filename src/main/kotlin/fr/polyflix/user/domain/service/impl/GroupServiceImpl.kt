package fr.polyflix.user.domain.service.impl

import fr.polyflix.user.domain.authentication.AuthenticatedUser
import fr.polyflix.user.domain.entity.Group
import fr.polyflix.user.domain.error.GroupWithoutOwnerError
import fr.polyflix.user.domain.error.UserHasNoSufficientPermissionsError
import fr.polyflix.user.domain.helper.Slug
import fr.polyflix.user.domain.persistence.repository.GroupRepository
import fr.polyflix.user.domain.persistence.repository.UserRepository
import fr.polyflix.user.domain.props.CreateGroupProps
import fr.polyflix.user.domain.props.UpdateGroupProps
import fr.polyflix.user.domain.service.GroupService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

class GroupServiceImpl(private val groupRepository: GroupRepository, private val userRepository: UserRepository): GroupService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create(props: CreateGroupProps): Group {
        logger.info("Creating group with name '${props.name}' and owner ${props.owner}, with ${props.members.size} member(s)")
        // A group cannot be created without a valid owner
        val owner = userRepository.findOne(props.owner)
            .orElseThrow { GroupWithoutOwnerError(props.name) }
        // Get all the members to assign
        val members = userRepository.findAllByIds(props.members)

        // We generate a unique identifier for the group and also a unique slug
        val id = UUID.randomUUID()
        val slug = "${Slug.make(props.name)}-${id.toString().substring(0, 5)}"
        logger.info("Slug '$slug' generated from group '${props.name}'")

        // Build the group and save it
        val group = Group(id, props.name, slug, owner, members.toMutableSet())
        return groupRepository.save(group)
    }

    override fun update(id: UUID, props: UpdateGroupProps, user: AuthenticatedUser): Optional<Group> {
        return groupRepository.getById(id).map {
            // If the user is not the owner of the group and not administrator, throw an error
            if (it.owner.id != user.id && !user.isAdmin) {
                throw UserHasNoSufficientPermissionsError(user.id)
            }

            logger.info("Updating group '${it.name}' with new props $props")
            if (props.name != null) it.name = props.name
            if (props.members != null) {
                it.members = userRepository.findAllByIds(props.members).toMutableSet()
            }
            groupRepository.save(it)
        }
    }

    override fun getAllPaginated(pageable: Pageable): Page<Group> {
        return groupRepository.getAllGroupsWithPagination(pageable)
    }

    override fun getOneBySlug(slug: String): Optional<Group> {
        return groupRepository.getBySlug(slug)
    }

    override fun delete(id: UUID, user: AuthenticatedUser): Optional<Group> {
        return groupRepository.getById(id).map {
            // If the user is not the owner of the group and not administrator, throw an error
            if (it.owner.id != user.id && !user.isAdmin) {
                throw UserHasNoSufficientPermissionsError(user.id)
            }
            logger.info("User ${user.id} has deleted the group '${it.name}'")
            groupRepository.delete(it)
        }
    }

    override fun getOneById(id: UUID): Optional<Group> {
        return groupRepository.getById(id)
    }
}