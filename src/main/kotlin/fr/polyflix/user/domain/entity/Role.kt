package fr.polyflix.user.domain.entity

import fr.polyflix.user.domain.enum.Roles
import java.util.UUID

class Role(val id: UUID, val name: Roles)