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

        val declarations = mutableListOf<Regex>()

        variableDeclarationPatterns.forEach { regex ->
            val matches = regex.findAll(userCode)
            declarations.addAll(this.cleanDeclarationMatches(matches))
        }

        declarations.forEach { declaration ->
            declaration.replace(userCode, "_")
        }



        return userCode
    }

    private fun cleanDeclarationMatches(matches: Sequence<MatchResult>): List<Regex> {
        val output = mutableListOf<Regex>()

        for(match in matches) {
            val shortenMatch = match.value.subSequence(3, match.value.length-1).toString()
            output.add(Regex(shortenMatch.trim()))
        }

        return output
    }
}