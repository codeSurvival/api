import com.esgi.codesurvival.domain.code.Algorithm
import com.esgi.codesurvival.domain.level.Constraint
import com.esgi.codesurvival.domain.level.Language
import com.esgi.codesurvival.domain.level.Regex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.util.Assert
import java.util.*

class ConstraintTest {

    @Test
    fun constraintShouldMatchCodeWithAppropriateRegex() {

        val constraint = Constraint(UUID.randomUUID(), "boucles", "no for loops", mutableListOf())
        val language = Language(UUID.randomUUID(), "Kotlin")
        val regex = Regex(UUID.randomUUID(),  "(\\sfor|^for)\\s*\\(".toRegex(), language)




        constraint.regexes.add(regex)

        val algorithm1 = Algorithm("for(i in set)", language)
        val algorithm2 = Algorithm("for (i in set)", language)
        val algorithm3 = Algorithm(" for(i in set)", language)
        val algorithm4 = Algorithm(" for (i in set)", language)
        val algorithm5 = Algorithm("baba \nfor (i in set)", language)
        Assertions.assertEquals(false, constraint.isRespected(algorithm1))
        Assertions.assertEquals(false, constraint.isRespected(algorithm2))
        Assertions.assertEquals(false, constraint.isRespected(algorithm3))
        Assertions.assertEquals(false, constraint.isRespected(algorithm4))
        Assertions.assertEquals(false, constraint.isRespected(algorithm5))

        val algorithm6 = Algorithm("for i in set)", language)
        val algorithm7 = Algorithm("affordable", language)
        val algorithm8 = Algorithm("ford", language)
        val algorithm9 = Algorithm("raifor", language)
        Assertions.assertEquals(true, constraint.isRespected(algorithm6))
        Assertions.assertEquals(true, constraint.isRespected(algorithm7))
        Assertions.assertEquals(true, constraint.isRespected(algorithm8))
        Assertions.assertEquals(true, constraint.isRespected(algorithm9))



    }
}