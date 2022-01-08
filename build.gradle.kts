@file:Suppress("LocalVariableName", "UNUSED_VARIABLE")

import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

sourceSets {
    val main by getting
    val test by getting

    val api by creating {
        java {
            srcDir("src/api/kotlin")
            resources.srcDir("src/api/resources")
        }

        this.compileClasspath += main.compileClasspath
        this.runtimeClasspath += main.runtimeClasspath
    }

    listOf(main, test).forEach {
        it.compileClasspath += api.output
        it.runtimeClasspath += api.output
    }
}

group = "fr.stardustenterprises"
version = "0.4.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("org.slf4j:slf4j-api:1.7.32")

    implementation("fr.stardustenterprises:plat4k:1.3.0")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    val dokkaHtml by getting(DokkaTask::class) {
        dokkaSourceSets {
            configureEach {
                sourceRoots.from(file("src/api/kotlin"))
                skipDeprecated.set(true)
            }
        }
    }

    jar {
        from(sourceSets["api"].output)
    }
}

val apiJar by tasks.registering(Jar::class) {
    archiveClassifier.set("api")
    from(sourceSets["api"].output)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
    from(sourceSets["api"].allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml)
}

artifacts {
    archives(apiJar)
    archives(sourcesJar)
    archives(javadocJar)
}

val projectName = project.name
val desc = "Yet another Native library extractor/loader for the JVM."
val devs = arrayOf("xtrm", "lambdagg")
val repo = "stardust-enterprises/$projectName"

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set(projectName)
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
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(project.properties["NEXUS_USERNAME"] as? String ?: return@sonatype)
            password.set(project.properties["NEXUS_PASSWORD"] as? String ?: return@sonatype)
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
