package fr.polyflix.user.application.http.controller

import fr.polyflix.user.application.http.dto.response.groupRequest.GroupRequestResponse
import fr.polyflix.user.application.http.dto.response.groupRequest.PaginatedGroupRequestResponse
import fr.polyflix.user.domain.service.GroupRequestService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/groups/{slug}/requests")
class GroupRequestController(private val groupRequestService: GroupRequestService) {
    @GetMapping
    fun getGroupRequests(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<PaginatedGroupRequestResponse> {
        val groupRequests = groupRequestService.getGroupRequests(PageRequest.of(page, size))
        return ResponseEntity.ok(PaginatedGroupRequestResponse(groupRequests))
    }

    @GetMapping("/{id}")
    fun getGroupById(@PathVariable id: UUID): ResponseEntity<GroupRequestResponse> {
        return groupRequestService.findGroupRequestById(id)
            .map { ResponseEntity.ok(GroupRequestResponse(it)) }
            .orElseGet { ResponseEntity.notFound().build() }
    }
}