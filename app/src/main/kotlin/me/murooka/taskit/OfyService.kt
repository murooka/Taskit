package me.murooka.taskit

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.impl.translate.opt.joda.JodaTimeTranslators
import me.murooka.taskit.dao.EmailAuthDAO
import me.murooka.taskit.dao.GithubAccountDAO
import me.murooka.taskit.dao.UserDAO

object OfyService {
    fun setup() {
        val factory = ObjectifyService.factory()
        JodaTimeTranslators.add(factory)

        factory.register(UserDAO::class.java)
        factory.register(EmailAuthDAO::class.java)
        factory.register(GithubAccountDAO::class.java)
    }
}

