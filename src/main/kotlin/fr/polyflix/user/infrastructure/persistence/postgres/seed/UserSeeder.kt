package fr.polyflix.user.infrastructure.persistence.postgres.seed

import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.infrastructure.persistence.postgres.SpringRoleRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringUserRepository
import fr.polyflix.user.infrastructure.persistence.postgres.entity.RoleEntity
import fr.polyflix.user.infrastructure.persistence.postgres.entity.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserSeeder(
    @Value("\${seeders.enabled}") private val seedersEnabled: Boolean,
    private val userRepository: SpringUserRepository,
    private val roleRepository: SpringRoleRepository,
): CommandLineRunner {
    private val logger = LoggerFactory.getLogger(javaClass)


    override fun run(vararg args: String?){
        logger.info("Checking for roles in database")

        Roles.values().forEach { createRoleIfNotFound(it) }

        if (!seedersEnabled) return

        val seeds = listOf(
            UserEntity(
                UUID.randomUUID(),
                "member@gmail.com",
                "member",
                "account",
                "member",
                "https://avatars.dicebear.com/api/identicon/member.svg",
                listOf(
                    roleRepository.findByName(Roles.Member).get()
                )
            ),
            UserEntity(
                UUID.randomUUID(),
                "contributor@gmail.com",
                "contributor",
                "account",
                "contributor",
                "https://avatars.dicebear.com/api/identicon/contributor.svg",
                listOf(
                    roleRepository.findByName(Roles.Contributor).get()
                )
            ),
            UserEntity(
                UUID.randomUUID(),
                "administrator@gmail.com",
                "administrator",
                "account",
                "administrator",
                "https://avatars.dicebear.com/api/identicon/administrator.svg",
                listOf(
                    roleRepository.findByName(Roles.Administrator).get()
                )
            )
        )

        logger.info("Cleaning seeds entity in table 'users'")
        userRepository.deleteAll(seeds)
        logger.info("Seeding table 'users' with ${seeds.size} elements.")
        userRepository.saveAll(seeds)
    }

    private fun createRoleIfNotFound(role: Roles): RoleEntity {
        return roleRepository
            .findByName(role)
            .orElseGet {
                logger.info("Creating role $role")
                roleRepository.save(RoleEntity(UUID.randomUUID(), role))
            }
    }
}