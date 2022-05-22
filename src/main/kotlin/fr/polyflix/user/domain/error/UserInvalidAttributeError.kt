package fr.polyflix.user.domain.error

import java.util.UUID

class UserInvalidAttributeError(id: UUID, attributes: List<String>): Exception("One or more attributes of the user $id are invalid: [${attributes.joinToString(",")}]")