package com.esgi.kotlin_game.pdk

data class MobState(var health: Int, var position: Coordinates) {
    fun isAlive(): Boolean {
        return health > 0
    }
}
