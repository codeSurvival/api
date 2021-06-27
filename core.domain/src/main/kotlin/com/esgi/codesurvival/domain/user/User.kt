package com.esgi.codesurvival.domain.user

import java.util.*

class User (
    val id: UserId,
    val username: String,
    val email: String,
    val password: String,
    var role: Role = Role.USER,
    var level : Int,
    var lastCodeId : UUID?
) {

    fun isUnique( accounts : List<User>) : Boolean {
        return accounts.none { this.isSimilarByNameOrEmail(it) }
    }

    private fun isSimilarByNameOrEmail(user: User): Boolean {
        return user.email == email || user.username == username
    }

    fun setRole( accounts : List<User> ) {
        if ( accounts.none {it.role == Role.ADMIN}) {
            this.role = Role.ADMIN
        }
    }
}