package com.esgi.kotlin_game.pdk_test

import com.esgi.kotlin_game.pdk.*
import com.esgi.kotlin_game.pdk.MobBehavior

class MobBehavior: MobBehavior {
    override fun decide(state: MobState): MobAction {
        return MobAction(ActionType.WALK, Direction.UP)
    }
}