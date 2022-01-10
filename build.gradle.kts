@file:Suppress("UNUSED_VARIABLE")

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.dokka")
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
version = "0.5.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("fr.stardustenterprises:plat4k:1.3.0")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    dokkaHtml {
        dokkaSourceSets.configureEach {
            sourceRoots.from(file("src/api/kotlin"))
            skipDeprecated.set(true)
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    /* Artifacts */

    // Main artifact, already containing the 'main' output. We're adding the API source output.
    jar {
        from(sourceSets["api"].output)
    }

    // API artifact, only including the output of the API source.
    val apiJar by creating(Jar::class) {
        group = "build"

        archiveClassifier.set("api")
        from(sourceSets["api"].output)
    }

    // Javadoc artifact, including everything Dokka creates.
    val javadocJar by creating(Jar::class) {
        group = "build"

        archiveClassifier.set("javadoc")
        dependsOn(dokkaHtml)
        from(dokkaHtml)
    }

    // Source artifact, including everything the 'main' does but not compiled.
    val sourcesJar by creating(Jar::class) {
        group = "build"

        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
        from(sourceSets["api"].allSource)
    }
}

val artifactTasks = arrayOf(
    tasks["apiJar"],
    tasks["sourcesJar"],
    tasks["javadocJar"]
)

artifacts {
    artifactTasks.forEach(::archives)
}

val projectName = project.name
val desc = "Yet Another Native Library loader and extractor for the JVM."
val authors = arrayOf("xtrm", "lambdagg")
val repo = "stardust-enterprises/$projectName"

publishing.publications {
    // Sets up the Maven integration.
    create<MavenPublication>("mavenJava") {
        from(components["java"])
        artifactTasks.forEach(::artifact)

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
                authors.forEach {
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

        // Configure the signing extension to sign this Maven artifact.
        signing.sign(this)
    }
}

// Set up the Sonatype artifact publishing.
nexusPublishing.repositories.sonatype {
    nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
    snapshotRepositoryUrl.set(
        uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    )

    // Skip this step if environment variables NEXUS_USERNAME or NEXUS_PASSWORD aren't set.
    username.set(properties["NEXUS_USERNAME"] as? String ?: return@sonatype)
    password.set(properties["NEXUS_PASSWORD"] as? String ?: return@sonatype)
}
