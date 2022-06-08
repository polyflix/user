package fr.polyflix.user.domain.error

class GroupWithoutOwnerError(group: String): Exception("Group $group does not have a valid owner.")