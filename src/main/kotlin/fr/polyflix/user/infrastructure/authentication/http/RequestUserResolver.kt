package fr.polyflix.user.infrastructure.authentication.http

import fr.polyflix.user.domain.authentication.AuthenticatedUser
import fr.polyflix.user.domain.enum.Roles
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.*
import javax.servlet.http.HttpServletRequest

class RequestUserResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest
        val id = request.getHeader("X-User-Id")
        val roles = request.getHeader("X-User-Roles").split(",").map { role ->
            val enum = role.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            Roles.valueOf(enum)
        }
        return AuthenticatedUser(UUID.fromString(id), roles)
    }
}