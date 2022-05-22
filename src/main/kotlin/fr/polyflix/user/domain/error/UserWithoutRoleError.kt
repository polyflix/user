package fr.polyflix.user.domain.error

class UserWithoutRoleError: Exception("The user should at least have one role to be valid.")