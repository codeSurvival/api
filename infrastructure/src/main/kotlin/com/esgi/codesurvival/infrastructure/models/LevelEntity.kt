package com.esgi.codesurvival.infrastructure.models

import javax.persistence.*

@Entity
@Table(name = "\"level\"")
class LevelEntity (
    @Id
    @Column(name = "id") var id: Int,

    @Column(name = "turn_objective") var turnObjective : Int,

)
