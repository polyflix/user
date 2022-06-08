package fr.polyflix.user.application.http.controller

import fr.polyflix.user.application.http.dto.response.group.GroupResponse
import fr.polyflix.user.application.http.dto.response.group.PaginatedGroupResponse
import fr.polyflix.user.domain.authentication.AuthenticatedUser
import fr.polyflix.user.domain.props.CreateGroupProps
import fr.polyflix.user.domain.props.UpdateGroupProps
import fr.polyflix.user.domain.service.GroupService
import fr.polyflix.user.infrastructure.authentication.http.RequestUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/groups")
class GroupController(private val groupService: GroupService) {
    @GetMapping
    fun getGroups(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<PaginatedGroupResponse> {
        val groups = groupService.getAllPaginated(PageRequest.of(page, size))
        return ResponseEntity.ok(PaginatedGroupResponse(groups))
    }

    @PostMapping
    fun createGroup(@RequestBody dto: CreateGroupProps): ResponseEntity<GroupResponse> {
        val group = groupService.create(dto)
        return ResponseEntity.ok(GroupResponse(group))
    }

    @GetMapping("/{slug}")
    fun getGroupBySlug(@PathVariable slug: String): ResponseEntity<GroupResponse> {
        return groupService.getOneBySlug(slug)
            .map { ResponseEntity.ok(GroupResponse(it)) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    @PutMapping("/{id}")
    fun updateGroup(@PathVariable id: UUID, @RequestBody props: UpdateGroupProps, @RequestUser authenticatedUser: AuthenticatedUser): ResponseEntity<GroupResponse> {
        return groupService.update(id, props, authenticatedUser)
            .map { ResponseEntity.ok(GroupResponse(it)) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    @DeleteMapping("/{id}")
    fun deleteGroup(@PathVariable id: UUID, @RequestUser authenticatedUser: AuthenticatedUser): ResponseEntity<GroupResponse> {
        return groupService.delete(id, authenticatedUser)
            .map { ResponseEntity.noContent().build<GroupResponse>() }
            .orElseGet { ResponseEntity.notFound().build() }
    }
}