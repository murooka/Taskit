package me.murooka.taskit.handler

import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


class UserHandler {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getInstances(): String {
        return "[]"
    }

}
