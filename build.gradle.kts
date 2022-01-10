import java.net.URL

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
val projectName = project.name
version = "0.5.0"
val desc = "Yet Another Native Library loader and extractor for the JVM."
val authors = arrayOf("xtrm", "lambdagg")
val repo = "stardust-enterprises/$projectName"

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
        val moduleFile = File(projectDir, "MODULE.temp.MD")

        run {
            // In order to have a description on the rendered docs, we have to have
            // a file with the # Module thingy in it. That's what we're
            // automagically creating here.

            doFirst {
                moduleFile.writeText("# Module $projectName\n$desc")
            }

            doLast {
                moduleFile.delete()
            }
        }

        moduleName.set(projectName)

        dokkaSourceSets.configureEach {
            displayName.set("$projectName github")
            includes.from(moduleFile.path)

            skipDeprecated.set(false)
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)
            reportUndocumented.set(true)

            sourceRoots.from(file("src/api/kotlin"))

            // Link the source to the documentation
            sourceLink {
                localDirectory.set(file("src"))
                remoteUrl.set(URL("https://github.com/$repo/tree/trunk/src"))
            }

            // Plat4k external documentation links
            externalDocumentationLink {
                packageListUrl.set(URL("https://stardust-enterprises.github.io/plat4k/plat4k/package-list"))
                url.set(URL("https://stardust-enterprises.github.io/plat4k/"))
            }
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
    create("apiJar", Jar::class) {
        group = "build"

        archiveClassifier.set("api")
        from(sourceSets["api"].output)
    }

    // Source artifact, including everything the 'main' does but not compiled.
    create("sourcesJar", Jar::class) {
        group = "build"

        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
        from(sourceSets["api"].allSource)
    }

    // Javadoc artifact, including everything Dokka creates.
    create("javadocJar", Jar::class) {
        group = "build"

        archiveClassifier.set("javadoc")
        dependsOn(dokkaHtml)
        from(dokkaHtml)
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
