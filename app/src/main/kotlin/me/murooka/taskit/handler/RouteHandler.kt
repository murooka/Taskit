package me.murooka.taskit.handler

import javax.ws.rs.Path

@Path("/")
class RouteHandler {
    @Path("/users")
    fun routeToUserHandler() = UserHandler()
}
