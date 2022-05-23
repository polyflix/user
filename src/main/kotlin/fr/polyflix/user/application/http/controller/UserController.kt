package fr.polyflix.user.application.http.controller

import fr.polyflix.user.application.http.dto.request.user.UpdateUserRequest
import fr.polyflix.user.application.http.dto.response.user.UserPaginatedResponse
import fr.polyflix.user.application.http.dto.response.user.UserResponse
import fr.polyflix.user.domain.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun findAll(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): ResponseEntity<UserPaginatedResponse> {
        val pageable = PageRequest.of(page - 1, size)
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
    fun updateOn(@PathVariable id: String, @RequestBody body: UpdateUserRequest): ResponseEntity<UserResponse>? {
        return userService
            .updateUser(UUID.fromString(id), body.username, body.firstName, body.lastName, body.avatar)
            .map {ResponseEntity.ok(UserResponse(it)) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: String): ResponseEntity<UserResponse>? {
        userService.deleteUser(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}