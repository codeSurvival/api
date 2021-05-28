package com.esgi.codesurvival.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "language")
class LanguageEntity (
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id")
    var id: UUID,

    @Column(name = "name") var name: String
)