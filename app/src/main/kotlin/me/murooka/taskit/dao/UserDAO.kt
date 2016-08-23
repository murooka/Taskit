package me.murooka.taskit.dao

import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.annotation.Cache
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id

@Entity
@Cache
class UserDAO() {
    @Id var id: Long? = null

    init {
    }

    constructor(id: Long) : this() {
        this.id = id
    }

    fun save() {
        ObjectifyService.ofy()
                .save()
                .entity(this)
                .now()
    }

    companion object {
        fun allocateId(): Long {
            val id = ObjectifyService.factory().allocateId(UserDAO::class.java)
            return id.id
        }
        fun keyForId(id: Long): Key<UserDAO> = Key.create(UserDAO::class.java, id)!!

        fun find(key: Key<UserDAO>): UserDAO? {
            return ObjectifyService.ofy()
                    .load()
                    .key(key)
                    .now()
        }
    }
}
