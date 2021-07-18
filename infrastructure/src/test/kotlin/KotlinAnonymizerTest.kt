import com.esgi.codesurvival.infrastructure.`code-anonymizer`.KotlinAnonymizer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class KotlinAnonymizerTest {

    @Test
    fun kotlinAnonymizerShouldReplaceVariableNamesBy_(){
        val userCode = "\nval doggo = 3 \ndoggo++ \nreturn doggo\n"

        val sut = KotlinAnonymizer()

        val anonymizedCode = sut.anonymize(userCode)

        Assertions.assertEquals("\nval _ = 3 \n" +
                "_++ \n" +
                "return _\n", anonymizedCode)
    }

    @Test
    fun kotlinAnonymizerShouldReplaceMultipleVariables(){
        val userCode = "\nval doggo = 3\n" +
                "val bob = 3\n" +
                "doggo++\n" +
                "return doggo\n"

        val sut = KotlinAnonymizer()

        val anonymizedCode = sut.anonymize(userCode)

        Assertions.assertEquals("\nval _ = 3\n" +
                "val _ = 3\n" +
                "_++\n" +
                "return _\n", anonymizedCode)
    }


    @Test
    fun kotlinAnonymizerShouldNotVariableNamesWhenPartOfAnotherWord(){
        val userCode = "\nval ret = 3 \n" +
                "ret++ \n" +
                "println(ret.toString())\n" +
                "return ret\n"

        val sut = KotlinAnonymizer()

        val anonymizedCode = sut.anonymize(userCode)

        Assertions.assertEquals("\nval _ = 3 \n" +
                "_++ \n" +
                "println(_.toString())\n" +
                "return _\n", anonymizedCode)
    }

    @Test
    fun kotlinAnonymizerShouldBehaveWhenMultipleVariableShareParts(){
        val userCode = "\nval doggo = 3 \n" +
                "var do = doggo +1 \n"+
                "var dogy = do-1 \n" +
                "doggo++ \n" +
                "return doggo\n"

        val sut = KotlinAnonymizer()

        val anonymizedCode = sut.anonymize(userCode)

        Assertions.assertEquals("\nval _ = 3 \n" +
                "var _ = _ +1 \n"+
                "var _ = _-1 \n" +
                "_++ \n" +
                "return _\n", anonymizedCode)
    }
}