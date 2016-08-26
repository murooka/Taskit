package me.murooka.taskit.handler.view

import me.murooka.taskit.model.User

data class UserView(
        val id: String,
        val createdAt: Long,
        val updatedAt: Long
) {
    companion object {
        fun fromModel(user: User): UserView = UserView(
                id = user.id.value,
                createdAt = user.createdAt.millis / 1000,
                updatedAt = user.updatedAt.millis / 1000
        )
    }
}

data class SessionView(
        val accessToken: String,
        val user: UserView
) {
    companion object {
        fun fromModel(session: String, user: User): SessionView = SessionView(
                accessToken = session,
                user = UserView.fromModel(user)
        )
    }
}