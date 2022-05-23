package fr.polyflix.user.infrastructure.authentication.keycloak.mapper

import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.domain.persistence.repository.RoleRepository
import fr.polyflix.user.infrastructure.authentication.keycloak.model.KeycloakUser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class KeycloakUserMapper(private val roleRepository: RoleRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun toDomain(keycloakUser: KeycloakUser): User {
        logger.info("Mapping KeycloakUser to domain User")
        val roles = listOf(roleRepository.findByName(Roles.Member).get())

        logger.info("Applying default roles to user : [${Roles.Member}]")
        return User.Builder()
            .withId(keycloakUser.id)
            .withEmail(keycloakUser.email)
            .withUsername(keycloakUser.username)
            .withFirstname(keycloakUser.firstName)
            .withLastname(keycloakUser.lastName)
            .withRoles(roles)
            .build()
    }

}