import org.junit.jupiter.api.Test

class KotlinAnonymizerTest {

    @Test
    fun kotlinAnonymizerShouldReplaceVariableNamesBy_(){
        val userCode = "val doggo = 3 \ndoggo++ \nreturn doggo"
    }
}