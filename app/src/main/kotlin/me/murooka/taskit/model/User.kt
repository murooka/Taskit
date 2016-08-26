package me.murooka.taskit.model

import org.joda.time.DateTime

data class GithubAccount(val id: Id) {
    data class Id(val value: String)
}

data class User(
        val id: Id,
        val github: GithubAccount? = null,
        val createdAt: DateTime,
        val updatedAt: DateTime
) {
    data class Id(val value: String)
}