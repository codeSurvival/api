import com.esgi.codesurvival.infrastructure.`code-anonymizer`.KotlinAnonymizer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KotlinAnonymizerTest {

    @Test
    fun kotlinAnonymizerShouldReplaceVariableNamesBy_(){
        val userCode = "val doggo = 3 \ndoggo++ \nreturn doggo"

        val sut = KotlinAnonymizer()

        val anonymizedCode = sut.anonymize(userCode)

        Assertions.assertEquals("val _ = 3 \n" +
                "_++ \n" +
                "return _", anonymizedCode)
    }
}