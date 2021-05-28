package com.esgi.codesurvival.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "regex")
class RegexEntity (
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID,

    @Column(name = "pattern") var pattern: String,

    @Type(type = "uuid-char")
    @Column(name = "language_id") var languageId: UUID,

    @Type(type = "uuid-char")
    @Column(name = "constraint_id") var constraintId: UUID,
)