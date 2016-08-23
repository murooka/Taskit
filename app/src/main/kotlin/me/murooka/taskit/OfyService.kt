package me.murooka.taskit

import com.googlecode.objectify.ObjectifyService
import me.murooka.taskit.dao.UserDAO

object OfyService {
    fun setup() {
        val factory = ObjectifyService.factory()
        // JodaTimeTranslators.add(factory)

        factory.register(UserDAO::class.java)
    }
}

