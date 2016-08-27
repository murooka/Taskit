package me.murooka.taskit.handler

import me.murooka.taskit.dao.EmailAuthDAO
import me.murooka.taskit.dao.UserDAO
import me.murooka.taskit.exception.InvalidParamException
import me.murooka.taskit.handler.view.SessionView
import me.murooka.taskit.service.GithubAccountService
import me.murooka.taskit.service.SessionService
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

data class PostRegisterRequest(val email: String?, val password: String?) {
    constructor() : this(null, null)
}

data class PostLoginGithubRequest(val accessToken: String?) {
    constructor() : this(null)
}

class AuthHandler {
    @POST
    @Path("/login/github")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postLoginGithub(req: PostLoginGithubRequest): SessionView {
        try {
            println(req)
            if (req.accessToken == null) throw InvalidParamException("/accessToken not specified")

            val user = GithubAccountService.findOrCreate(req.accessToken)
            val session = SessionService.generate(user)

            return SessionView.fromModel(session, user)
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            println(e.cause?.message)
            throw RuntimeException(e)
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postRegister(req: PostRegisterRequest) {
        if (req.email == null) throw InvalidParamException("/email not specified")
        if (req.password == null) throw InvalidParamException("/password not specified")

        val user = UserDAO(UserDAO.allocateId())
        user.save()

        val emailAuth = EmailAuthDAO(req.email, req.password, user.id)
        emailAuth.save()
    }
}

