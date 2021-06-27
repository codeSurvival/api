package com.esgi.codesurvival.application.code.execution.anonymizer

interface Anonymizer {

    fun anonymize(userCode : String) : String;
}