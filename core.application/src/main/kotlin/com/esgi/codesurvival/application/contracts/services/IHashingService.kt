package com.esgi.codesurvival.application.contracts.services

interface IHashingService {

    fun hashPassword(password: String) : String

    fun matches(password: String, hashedPassword: String): Boolean

    fun getNewAccessKey() : String
}