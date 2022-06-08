package fr.polyflix.user.domain.error

class UserAlreadyInGroupError(user: String, group: String): Exception("User $user is already member of group $group")