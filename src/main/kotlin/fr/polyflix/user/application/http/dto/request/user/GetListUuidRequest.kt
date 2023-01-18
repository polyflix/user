package fr.polyflix.user.application.http.dto.request.user

import fr.polyflix.user.domain.enum.Roles
import java.util.UUID

data class GetListUuidRequest(
    val ids: List<UUID>,
)