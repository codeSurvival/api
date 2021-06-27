package com.esgi.codesurvival.infrastructure.repositories

import com.esgi.codesurvival.infrastructure.models.CodeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface CodeRepository : JpaRepository<CodeEntity, UUID> {
}