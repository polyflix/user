package fr.polyflix.user.domain.error

import java.util.UUID

class UserHasNoSufficientPermissionsError(user: UUID): Exception("User $user is not administrator or has no sufficient permissions to do this action.")