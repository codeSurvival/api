package com.esgi.codesurvival.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "\"code\"")
class CodeEntity (

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID,

    @Column(name = "value") var value: String,

    @Type(type = "uuid-char")
    @Column(name = "language_id") var languageId: UUID
)