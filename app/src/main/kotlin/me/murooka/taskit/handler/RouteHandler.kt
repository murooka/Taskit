package me.murooka.taskit.handler

import javax.ws.rs.Path

@Path("/")
class RouteHandler {
    @Path("/auth")
    fun routeToAuthHandler() = AuthHandler()
}
