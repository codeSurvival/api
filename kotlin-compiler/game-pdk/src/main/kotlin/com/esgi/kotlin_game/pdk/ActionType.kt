package com.esgi.kotlin_game.pdk

enum class ActionType(val needsDirection: Boolean) {
    WALK(true),
    TRIPPED(false),
}
