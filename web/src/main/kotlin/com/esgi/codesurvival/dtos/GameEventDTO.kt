package com.esgi.codesurvival.dtos

data class GameEventDTO(val world: WorldDTO, val action: MobAction?, val gameover: Boolean = false, val user: String, val gameLoss: Boolean)


data class MobAction(val type: ActionType, val target: Direction? = null) {

}

enum class ActionType(val needsDirection: Boolean) {
    WALK(true),
    TRIPPED(false),
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class Grid (val tiles: List<Tile>)

class Tile(val coordonates: Coordinates, val containt: List<TileContaint>, val type: TileType)

enum class TileType {
    GRASS,
    PIT,
}

data class Mob(val state: MobState) {

}

data class MobState(var health: Int, var position: Coordinates) {

}

data class Coordinates(val x: Int, val y: Int) {

}



data class WorldDTO(val mobDTO: MobDTO, val grid: GridDTO, val round: Int) {

}

data class GridDTO(val tiles: List<TileDTO>) {

}

data class TileDTO(val coordonates: Coordinates, val containt: List<TileContaint>, val type: TileType) {
}

data class MobDTO(val mobState: MobState) {

}

enum class TileContaint {
    MEAL,
}