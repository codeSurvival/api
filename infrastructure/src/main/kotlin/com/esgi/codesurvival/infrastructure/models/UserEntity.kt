package com.esgi.codesurvival.infrastructure.models

import com.esgi.codesurvival.domain.user.Role
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "user")
class UserEntity (
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id") var id: UUID,

    @Column(name = "username") var username: String,

    @Column(name = "email") var email: String,

    @Column(name = "password") var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role") var role: Role

)