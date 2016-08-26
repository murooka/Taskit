package me.murooka.taskit.dao

import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.Ref
import com.googlecode.objectify.annotation.*
import org.joda.time.DateTime

@Entity
@Cache
class GithubAccountDAO() {
    @Id var id: Long = 0
    @Index lateinit var user: Ref<UserDAO>
    var createdAt: DateTime? = null
    var updatedAt: DateTime? = null

    constructor(id: Long, user: Ref<UserDAO>) : this() {
        this.id = id
        this.user = user
    }

    @OnSave
    @Suppress("UNUSED_PARAMETER")
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
        fun keyForId(id: Long): Key<GithubAccountDAO> = Key.create(GithubAccountDAO::class.java, id)!!

        fun find(id: Long) = find(keyForId(id))
        fun find(key: Key<GithubAccountDAO>): GithubAccountDAO? {
            println("find")
            return ObjectifyService.ofy()
                    .load()
                    .key(key)
                    .now()
        }
    }
}

