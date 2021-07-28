package com.esgi.codesurvival.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "\"code\"")
class CodeEntity (

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID,

    @Column(name = "value", columnDefinition = "text")
    var value: String,

    @Type(type = "uuid-char")
    @Column(name = "language_id") var languageId: UUID
)