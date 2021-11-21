plugins {
    kotlin("jvm") version "1.6.0"
}

val api = kotlin.sourceSets.create("api") {
    kotlin.srcDir("src/api/kotlin")
    resources.srcDir("src/api/resources")
}

kotlin.sourceSets["main"].dependsOn(api)

group = "fr.stardustenterprises"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}