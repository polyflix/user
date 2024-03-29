package fr.polyflix.user.application.http.controller

import fr.polyflix.user.application.http.dto.request.user.GetListUuidRequest
import fr.polyflix.user.application.http.dto.request.user.UpdateUserRequest
import fr.polyflix.user.application.http.dto.response.user.UserPaginatedResponse
import fr.polyflix.user.application.http.dto.response.user.UserResponse
import fr.polyflix.user.domain.entity.User
import fr.polyflix.user.domain.enum.Roles
import fr.polyflix.user.domain.service.UserService
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun findAll(
            @RequestParam(required = false, defaultValue = "0") page: Int,
            @RequestParam(required = false, defaultValue = "10") size: Int,
            @RequestHeader("X-User-Id") userId: String,
            @RequestHeader("X-User-Roles") userRoles: String,
    ): ResponseEntity<UserPaginatedResponse> {
        val roles = userRoles.split(",")
        if (!isAdmin(roles)) {
            logger.warn("Forbidden access. $userId is not administrator.")
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
        val pageable = PageRequest.of(page, size)
        val data = userService.getUsers(pageable)

        return ResponseEntity.ok(UserPaginatedResponse(data))
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): ResponseEntity<UserResponse>? {
        return userService
                .findUserById(UUID.fromString(id))
                .map { ResponseEntity.ok(UserResponse(it)) }
                .orElseGet { ResponseEntity.notFound().build() }
    }

    @PutMapping("/{id}")
    fun updateOn(
            @PathVariable id: String,
            @RequestBody body: UpdateUserRequest,
            @RequestHeader("X-User-Id") userId: String,
            @RequestHeader("X-User-Roles") userRoles: String
    ): ResponseEntity<UserResponse>? {
        val roles = userRoles.split(",")
        if (!hasSufficientPermissions(id, userId, roles)) {
            logger.warn(
                    "Forbidden update. $userId is not administrator or isn't the resource owner of the user : $id."
            )
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

        return userService
                .updateUser(
                        UUID.fromString(id),
                        body.username,
                        body.firstName,
                        body.lastName,
                        body.avatar,
                        // We want to use the roles in the request only if the
                        // authenticated user is administrator
                        if (isAdmin(roles)) body.roles else null
                )
                .map { ResponseEntity.ok(UserResponse(it)) }
                .orElseGet { ResponseEntity.notFound().build() }
    }

    @GetMapping("/bulk")
    fun findUsersById(@RequestBody body: GetListUuidRequest): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.findUsersById(body.ids).map { UserResponse(it) })
    }

    private fun hasSufficientPermissions(
            subject: String,
            user: String,
            userRoles: List<String>
    ): Boolean {
        return subject == user || isAdmin(userRoles)
    }

    private fun isAdmin(userRoles: List<String>): Boolean {
        return userRoles.contains(Roles.Administrator.name.uppercase())
    }
}
