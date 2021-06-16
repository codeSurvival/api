package com.esgi.codesurvival.application.rabbit

interface Sender {
    fun send(message: String, queueName: String)
}