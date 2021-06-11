plugins {
    kotlin("jvm")
}

tasks.jar {
    archiveFileName.set("plugin.jar")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":kotlin-compiler:game-pdk"))
}
