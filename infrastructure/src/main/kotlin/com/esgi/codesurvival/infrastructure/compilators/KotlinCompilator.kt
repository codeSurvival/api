package com.esgi.codesurvival.infrastructure.compilators

import com.esgi.codesurvival.application.code.execution.compilator.Compilator
import com.esgi.codesurvival.application.code.execution.compilator.CompilatorPaths
import com.esgi.codesurvival.infrastructure.mappers.randomValue
import com.esgi.codesurvival.infrastructure.mappers.runCommand
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths

@Component
class KotlinCompilator : Compilator {
    private val appRoot = Paths.get("").toAbsolutePath().toString()
    private val kotlinCompilatorPath = "kotlin-compiler"

    private val pdkTemplate = File("$appRoot/$kotlinCompilatorPath/empty-pdk")
    private val gradleSettingsTemplate = File("$appRoot/$kotlinCompilatorPath/example.gradle")

    private val defaultBodyMobBehavior = "return MobAction(ActionType.WALK, Direction.UP)"
    private val mobBehaviorPath = "src/main/kotlin/com/esgi/kotlin_game/pdk_test/MobBehavior.kt"

    override fun compileAndExecute(compilatorPaths: CompilatorPaths) {
        ("gradle kotlin-compiler:${compilatorPaths.moduleName}:build -c ${compilatorPaths.settingsGradleFileName}")
            .runCommand(File(appRoot))


        ("docker run --rm --env-file .env --network setted-network " +
                "--mount type=bind,source=$appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}/build/libs,target=/app/plugins" +
                " --name kokuu30 kotlin-game/api").runCommand(File(appRoot))
    }

    override fun buildEntrypoint(): CompilatorPaths {
        val newModuleName = createDirectory()
        val newSettingGradleName = createSettingsFile(newModuleName)

        return CompilatorPaths(newSettingGradleName, newModuleName)
    }

    override fun clean(compilatorPaths: CompilatorPaths) {
        val directoryPathToDelete = File("$appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}")
        directoryPathToDelete.deleteRecursively()

        val gradleFileToDelete = File("$appRoot/${compilatorPaths.settingsGradleFileName}")
        gradleFileToDelete.delete()
    }

    private fun createSettingsFile(newModuleName: String): String {
        val newGradleName = "$newModuleName.gradle.kts"
        val directoryPath = "$appRoot/$newGradleName"
        val directoryToCreateIn = File(directoryPath)

        gradleSettingsTemplate.copyTo(
            target = directoryToCreateIn,
            overwrite = true
        )

        val newGradleFile = File("$directoryPath")
        newGradleFile.appendText("include(\":$kotlinCompilatorPath:$newModuleName\")")

        return newGradleName
    }

    private fun createDirectory(): String {
        val directoryName = "".randomValue(10)
        val path = "$appRoot/$kotlinCompilatorPath/$directoryName"
        val directoryToCreateIn = File(path)

        pdkTemplate.copyRecursively(
            target = directoryToCreateIn,
            overwrite = true,
        )

        return directoryName
    }

    override fun addUserCode(userCode: String, compilatorPaths: CompilatorPaths) {
        val fileToModify = File("$appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}/$mobBehaviorPath")

        var mobBehaviorFileContent = fileToModify.readText()
        var fileContent = mobBehaviorFileContent.replace(defaultBodyMobBehavior, userCode)

        fileToModify.writeText(fileContent)
    }
}