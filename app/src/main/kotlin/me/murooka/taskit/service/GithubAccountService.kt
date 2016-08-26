package me.murooka.taskit.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.appengine.api.urlfetch.HTTPHeader
import com.google.appengine.api.urlfetch.HTTPMethod
import com.google.appengine.api.urlfetch.HTTPRequest
import com.google.appengine.api.urlfetch.URLFetchServiceFactory
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.Ref
import me.murooka.taskit.dao.GithubAccountDAO
import me.murooka.taskit.dao.UserDAO
import me.murooka.taskit.model.User
import org.joda.time.DateTime
import java.net.URL

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubUser(
        @JsonProperty("id") val id: Long
)

inline fun <reified T : Any> ObjectMapper.read(json: String): T? = try {
    readValue(json, T::class.java)
} catch (e: Exception) {
    println(e)
    null
}

object GithubAccountService {
    private fun createUserWithGithubAccount(githubUserId: Long): GithubAccountDAO {
        println("create with github account")
        return ObjectifyService.ofy().transact<GithubAccountDAO> {
            val userId = UserDAO.allocateId()
            val userDAO = UserDAO(userId)
            userDAO.save()

            val githubAccountDAO = GithubAccountDAO(githubUserId, Ref.create(userDAO))
            githubAccountDAO.save()

            githubAccountDAO
        }
    }

    fun findOrCreate(accessToken: String): User {
        val urlfetch = URLFetchServiceFactory.getURLFetchService()
        val url = URL("https://api.github.com/user?access_token=$accessToken")
        val req = HTTPRequest(url, HTTPMethod.GET).apply {
            addHeader(HTTPHeader("User-Agent", "taskit"))
        }
        val res = urlfetch.fetch(req)

        if (res.responseCode !in 200..299) {
            throw RuntimeException("Failed to fetch github user")
        }

        val objectMapper = ObjectMapper()
        val githubUser: GithubUser? = objectMapper.read(String(res.content))

        githubUser ?: throw RuntimeException("Failed to parse github user")

        val dao = GithubAccountDAO.find(githubUser.id) ?: createUserWithGithubAccount(githubUser.id)
        val userDAO = dao.user.get()
        return User(
                id = User.Id(userDAO.id),
                createdAt = userDAO.createdAt ?: DateTime.now(),
                updatedAt = userDAO.updatedAt ?: DateTime.now()
        )
    }

}
