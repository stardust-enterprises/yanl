import java.net.URL

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

val projectName = project.name
group = "fr.stardustenterprises"
version = "0.7.3"

val desc = "Yet Another Native Library loader and extractor for the JVM."
val authors = arrayOf("xtrm", "lambdagg")
val repo = "stardust-enterprises/$projectName"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j", "slf4j-api", "1.7.33")
    implementation("fr.stardustenterprises", "plat4k", "1.6.2")

    testImplementation(kotlin("test"))
}

sourceSets {
    val main by getting
    val test by getting

    val api by creating {
        java.srcDir("src/api/kotlin")
        resources.srcDir("src/api/resources")

        this.compileClasspath += main.compileClasspath
        this.runtimeClasspath += main.runtimeClasspath
    }

    listOf(main, test).forEach {
        it.compileClasspath += api.output
        it.runtimeClasspath += api.output
    }
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

    // The original artifact, we just have to add the API source output and the
    // LICENSE file.
    jar {
        from(sourceSets["api"].output)
        from("LICENSE")
    }

    // API artifact, only including the output of the API source and the
    // LICENSE file.
    create("apiJar", Jar::class) {
        group = "build"

        archiveClassifier.set("api")
        from(sourceSets["api"].output)

        from("LICENSE")
    }

    // Source artifact, including everything the 'main' does but not compiled.
    create("sourcesJar", Jar::class) {
        group = "build"

        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
        from(sourceSets["api"].allSource)

        from("LICENSE")
    }

    // The Javadoc artifact, containing the Dokka output and the LICENSE file.
    create("javadocJar", Jar::class) {
        group = "build"

        archiveClassifier.set("javadoc")
        dependsOn(dokkaHtml)
        from(dokkaHtml)

        from("LICENSE")
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
