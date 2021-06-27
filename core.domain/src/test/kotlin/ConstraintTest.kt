import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.code.ConstraintRegex
import com.esgi.codesurvival.domain.code.LevelConstraint
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.domain.level.Regex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ConstraintTest {

    @Test
    fun constraintShouldMatchCodeWithAppropriateRegex() {

        val constraint = LevelConstraint(
            1,
            UUID.randomUUID(),
            "no for loops",
            "vous avez utilisé une boucle",
            mutableListOf())
        val language = Language(UUID.randomUUID(), "Kotlin")
        val regex = ConstraintRegex("(\\sfor|^for)\\s*\\(".toRegex(), language.id)




        constraint.regexes.add(regex)

        val algorithm1 = Algorithm("for(i in set)", language.id)
        val algorithm2 = Algorithm("for (i in set)", language.id)
        val algorithm3 = Algorithm(" for(i in set)", language.id)
        val algorithm4 = Algorithm(" for (i in set)", language.id)
        val algorithm5 = Algorithm("baba \nfor (i in set)", language.id)
        Assertions.assertEquals(false, constraint.isRespected(algorithm1))
        Assertions.assertEquals(false, constraint.isRespected(algorithm2))
        Assertions.assertEquals(false, constraint.isRespected(algorithm3))
        Assertions.assertEquals(false, constraint.isRespected(algorithm4))
        Assertions.assertEquals(false, constraint.isRespected(algorithm5))

        val algorithm6 = Algorithm("for i in set)", language.id)
        val algorithm7 = Algorithm("affordable", language.id)
        val algorithm8 = Algorithm("ford", language.id)
        val algorithm9 = Algorithm("raifor", language.id)
        Assertions.assertEquals(true, constraint.isRespected(algorithm6))
        Assertions.assertEquals(true, constraint.isRespected(algorithm7))
        Assertions.assertEquals(true, constraint.isRespected(algorithm8))
        Assertions.assertEquals(true, constraint.isRespected(algorithm9))



    }
}