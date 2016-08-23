package me.murooka.taskit.service

import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType
import java.util.Map

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class PostCreateRequest {
    public var id: String? = null
}

@Path("tasks")
class Task() {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getInstances(): String {
        return "[]"
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postCreate(req: PostCreateRequest): String {
        return "{ \"id\": \"${req.id}\" }"
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getSelf(@PathParam("id") id: String): String {
        return "{ \"id\": \"${id}\" }"
    }

}
