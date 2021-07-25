package com.esgi.codesurvival.dtos

import com.esgi.codesurvival.application.game.error.GameErrorType

data class GameEventDTO(val world: WorldDTO?, val action: MobAction?, val gameover: Boolean? = false, val user: String, val gameLoss: Boolean?, val error: GameErrorDTO?)

data class GameErrorDTO(val type: GameErrorType, val message: String)

data class MobAction(val type: ActionType, val target: Direction? = null) {

}

enum class ActionType(val needsDirection: Boolean) {
    WALK(true),
    TRIPPED(false),
    SCOUT(true),
    JUMP(true),
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class CoordinatesDTO(val x: Int, val y: Int) {

}

enum class TileType {
    GRASS,
    PIT,
    TREE,
}

data class MobStateDTO(val needs: HashMap<MobNeeds, Int>, var position: CoordinatesDTO, var memoryTile: List<TileDTO>, val memory: HashMap<String, String>) {

}

enum class MobNeeds {
    BLOOD,
    HUNGER,
}


data class WorldDTO(val mobDTO: MobDTO, val grid: GridDTO, val round: Int) {

}

data class GridDTO(val tiles: List<TileDTO>, val gridSize: Int) {

}

data class TileDTO(val coordinates: CoordinatesDTO, val containt: List<TileContaint>, val type: TileType) {
}

data class MobDTO(val mobState: MobStateDTO) {

}

enum class TileContaint {
    MEAL,
}