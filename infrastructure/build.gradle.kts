plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}


val javaJwtVersion = extra.get("javaJwtVersion")

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


    implementation(project(":core.domain"))
    implementation(project(":core.application"))

    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("org.apache.commons:commons-text:1.9")

    implementation("c3p0:c3p0:0.9.0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")




}
