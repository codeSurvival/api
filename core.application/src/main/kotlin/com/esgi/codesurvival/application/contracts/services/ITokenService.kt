package com.esgi.codesurvival.application.contracts.services

interface ITokenService {
    fun parse(token: String): String
    fun sign(claim: String): String
}
