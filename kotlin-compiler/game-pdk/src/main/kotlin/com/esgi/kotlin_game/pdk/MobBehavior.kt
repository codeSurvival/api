package com.esgi.kotlin_game.pdk

interface MobBehavior {
    fun decide(state: MobState): MobAction
}