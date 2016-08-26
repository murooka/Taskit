package me.murooka.taskit.filter

import me.murooka.taskit.service.SessionService
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter

class AuthenticationFilter : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext?) {
        requestContext ?: return

        val authorizationHeaders = requestContext.headers["Authorization"] ?: return
        val authorizationHeader = authorizationHeaders[0] ?: return
        println("Header = " + authorizationHeader)

        val split = authorizationHeader.split("\\s+")
        if (split.size < 2) return

        val scheme = split[0]
        if (scheme != "Bearer") return

        val jws = split[1]
        val session = SessionService.verify(jws)
        println("session = " + session)

        requestContext.setProperty("taskit-session", session)
    }
}