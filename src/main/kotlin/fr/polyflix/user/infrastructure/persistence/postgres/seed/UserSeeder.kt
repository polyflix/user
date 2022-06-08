package fr.polyflix.user.infrastructure.persistence.postgres.seed

import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.infrastructure.persistence.postgres.SpringGroupRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringRoleRepository
import fr.polyflix.user.infrastructure.persistence.postgres.SpringUserRepository
import fr.polyflix.user.infrastructure.persistence.postgres.entity.GroupEntity
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
    private val groupRepository: SpringGroupRepository
): CommandLineRunner {
    private val logger = LoggerFactory.getLogger(javaClass)


    override fun run(vararg args: String?){
        logger.info("Checking for roles in database")

        Roles.values().forEach { createRoleIfNotFound(it) }

        if (!seedersEnabled) return

        val memberUser = UserEntity(
            UUID.fromString("5efc9dce-35e3-4540-bb00-d31fafdd2849"),
            "member@gmail.com",
            "member",
            "account",
            "member",
            "https://avatars.dicebear.com/api/identicon/member.svg",
            listOf(
                roleRepository.findByName(Roles.Member).get()
            )
        )

        val contributorUser = UserEntity(
            UUID.fromString("4b477cae-4cbc-47ac-805b-415d1ecbe68f"),
            "contributor@gmail.com",
            "contributor",
            "account",
            "contributor",
            "https://avatars.dicebear.com/api/identicon/member.svg",
            listOf(
                roleRepository.findByName(Roles.Contributor).get()
            )
        )

        val administratorUser = UserEntity(
            UUID.fromString("9734ebaf-29ec-448a-a285-c8ef460da824"),
            "administrator@gmail.com",
            "administrator",
            "account",
            "administrator",
            "https://avatars.dicebear.com/api/identicon/administrator.svg",
            listOf(
                roleRepository.findByName(Roles.Administrator).get()
            )
        )

        val userSeeds = listOf(memberUser, contributorUser, administratorUser)

        val groupSeeds = listOf(
            GroupEntity(
                UUID.fromString("66ed3fd4-902d-414c-a96f-dc1232a2c1af"),
                "DO4",
                "do4",
                administratorUser,
                setOf(memberUser, contributorUser, administratorUser)
            ),
            GroupEntity(
                UUID.fromString("ce3848ce-742f-4467-ab9d-3c5889cf282f"),
                "DO3",
                "do3",
                contributorUser,
                setOf(memberUser)
            )
        )

        logger.info("Cleaning seeds entity in table 'groups'")
        // groupRepository.deleteAll(groupSeeds)
        logger.info("Cleaning seeds entity in table 'users'")
        // userRepository.deleteAll(userSeeds)

        logger.info("Seeding table 'users' with ${userSeeds.size} elements.")
        userRepository.saveAll(userSeeds)
        logger.info("Seeding table 'groups' with ${groupSeeds.size} elements.")
        groupRepository.saveAll(groupSeeds)
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