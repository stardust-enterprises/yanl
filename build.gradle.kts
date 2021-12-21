@file:Suppress("LocalVariableName")

plugins {
    kotlin("jvm") version "1.6.0"
    `java-library`
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.6.0"
}

val api = kotlin.sourceSets.create("api") {
    kotlin.srcDir("src/api/kotlin")
    resources.srcDir("src/api/resources")
}
kotlin.sourceSets["main"].dependsOn(api)

group = "fr.stardustenterprises"
version = "0.4.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("org.slf4j:slf4j-api:1.7.32")

    implementation("fr.stardustenterprises:plat4k:1.1.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml)
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

val libraryName = "yanl"
val desc = "Yet another Native library extractor/loader for the JVM."
val devs = arrayOf("xtrm", "lambdagg")
val repo = "stardust-enterprises/$libraryName"

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set(libraryName)
                description.set(desc)
                url.set("https://github.com/$repo")
                licenses {
                    license {
                        name.set("ISC License")
                        url.set("https://opensource.org/licenses/ISC")
                        distribution.set("repo")
                    }
                }
                developers {
                    devs.forEach {
                        developer {
                            id.set(it)
                            name.set(it)
                        }
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/$repo.git")
                    developerConnection.set("scm:git:ssh://github.com/$repo.git")
                    url.set("https://github.com/$repo")
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                username = project.properties["NEXUS_USERNAME"] as? String
                password = project.properties["NEXUS_PASSWORD"] as? String
            }

            name = "Sonatype"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
