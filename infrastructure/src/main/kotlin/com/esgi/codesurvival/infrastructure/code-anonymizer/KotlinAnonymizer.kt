package com.esgi.codesurvival.infrastructure.`code-anonymizer`

import com.esgi.codesurvival.application.code.execution.anonymizer.Anonymizer
import org.springframework.stereotype.Component

@Component
class KotlinAnonymizer : Anonymizer {

    val variableDeclarationPatterns = listOf<Regex>(Regex("(val|var)\\s+\\w*\\s*[=:]"))

    val prefixUsageDelimitors = listOf<Char>(
        '\n',
        '=',
        ' ',
        '+',
        '-',
        '*',
        '.',
        ';',
        '(',
        '[',
        '<',
        '>')

    val suffixUsageDelimitors = listOf<Char>(
        '\n',
        '=',
        ' ',
        '+',
        '-',
        '*',
        '.',
        ';',
        ')',
        '[',
        ']',
        '<',
        '>')



    override fun anonymize(userCode: String): String {

        val declarations = mutableListOf<String>()

        variableDeclarationPatterns.forEach { regex ->
            val matches = regex.findAll(userCode)
            declarations.addAll(this.cleanDeclarationMatches(matches))


        }



        return userCode
    }

    private fun cleanDeclarationMatches(matches: Sequence<MatchResult>): List<String> {
        val output = mutableListOf<String>()

        for(match in matches) {
            val shortenMatch = match.value.subSequence(3, match.value.length-1)
            output.add(shortenMatch.trim() as String)
        }

        return output
    }
}