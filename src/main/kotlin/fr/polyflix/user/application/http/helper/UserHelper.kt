package fr.polyflix.user.application.http.helper

/**
 * Temporary object to avoid duplication code for
 * checking if the user has sufficient permissions to do an action
 */
object UserHelper {
    fun hasSufficientPermissions(subject: String, user: String, userRoles: String): Boolean {
        return subject == user || isAdmin(userRoles)
    }

    fun isAdmin(roles: String): Boolean {
        return extractRoles(roles).contains("ADMINISTRATOR")
    }

    private fun extractRoles(roles: String) = roles.split(",")
}
