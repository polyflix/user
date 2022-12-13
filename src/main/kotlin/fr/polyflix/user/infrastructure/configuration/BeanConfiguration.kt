package fr.polyflix.user.infrastructure.configuration

import fr.polyflix.user.domain.messaging.producer.UserProducer
import fr.polyflix.user.domain.persistence.repository.GroupRepository
import fr.polyflix.user.domain.persistence.repository.GroupRequestRepository
import fr.polyflix.user.domain.persistence.repository.RoleRepository
import fr.polyflix.user.domain.persistence.repository.UserRepository
import fr.polyflix.user.domain.service.GroupRequestService
import fr.polyflix.user.domain.service.GroupService
import fr.polyflix.user.domain.service.UserService
import fr.polyflix.user.domain.service.impl.GroupRequestServiceImpl
import fr.polyflix.user.domain.service.impl.GroupServiceImpl
import fr.polyflix.user.domain.service.impl.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun userService(
        userRepository: UserRepository,
        roleRepository: RoleRepository,
        userProducer: UserProducer
    ): UserService {
        return UserServiceImpl(userRepository, roleRepository, userProducer)
    }

    @Bean
    fun groupService(userRepository: UserRepository, groupRepository: GroupRepository): GroupService {
        return GroupServiceImpl(groupRepository, userRepository)
    }

    @Bean
    fun groupRequestService(groupRequestRepository: GroupRequestRepository): GroupRequestService {
        return GroupRequestServiceImpl(groupRequestRepository)
    }
}