package com.esgi.codesurvival.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "\"constraint\"")
class ConstraintEntity(
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID,

    @Column(name = "name") var name: String,

    @Column(name = "warning") var warning: String,

    @Column(name = "level_id") var levelId: Int
)