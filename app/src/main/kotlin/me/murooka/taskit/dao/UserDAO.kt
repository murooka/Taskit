package me.murooka.taskit.dao

import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.annotation.*
import org.joda.time.DateTime

@Entity
@Cache
class UserDAO() {
    @Id lateinit var id: String
    var createdAt: DateTime? = null
    var updatedAt: DateTime? = null

    constructor(id: String) : this() {
        this.id = id
    }

    @OnSave @Suppress("UNUSED")
    fun prepareToSave() {
        val now = DateTime.now()
        if (this.createdAt == null) {
            this.createdAt = now
        }
        this.updatedAt = now
    }

    fun save() {
        ObjectifyService.ofy()
                .save()
                .entity(this)
                .now()
    }

    companion object {
        fun allocateId(): String {
            val id = ObjectifyService.factory().allocateId(UserDAO::class.java)
            return id.string
        }
        fun keyForId(id: String): Key<UserDAO> = Key.create(UserDAO::class.java, id)!!

        fun find(key: Key<UserDAO>): UserDAO? {
            return ObjectifyService.ofy()
                    .load()
                    .key(key)
                    .now()
        }
    }
}
