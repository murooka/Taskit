package me.murooka.taskit.handler

import me.murooka.taskit.dao.UserDAO
import javax.print.attribute.standard.Media
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


class UserHandler {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getInstances(): String {
        return "[]"
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getSelf(@PathParam("id") id: Long): String {
        val user = UserDAO.find(UserDAO.keyForId(id))

        return """{"id":${user?.id}}"""
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postCreate(): String {
        val id = UserDAO.allocateId()
        val user = UserDAO(id = id)
        user.save()

        return """{"id":${user.id}}"""
    }

}
