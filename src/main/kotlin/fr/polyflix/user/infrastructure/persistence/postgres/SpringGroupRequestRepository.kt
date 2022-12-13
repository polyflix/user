package fr.polyflix.user.infrastructure.persistence.postgres

import fr.polyflix.user.infrastructure.persistence.postgres.entity.GroupRequestEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SpringGroupRequestRepository : JpaRepository<GroupRequestEntity, UUID>