package com.esgi.codesurvival.application.sse

enum class SseMessage(val message: String) {
    WIN_GAME("Vous avez gagné la partie !"),
    LOSE_GAME("Votre créature est morte, vous avez perdu la partie...")
}