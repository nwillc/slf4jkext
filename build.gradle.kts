/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    `maven-publish`
    Dependencies.plugins.forEach { (n, v) -> id(n) version v }
}

group = "com.github.nwillc"
version = "1.1.2"

repositories {
    jcenter()
}

dependencies {
    Dependencies.artifacts(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8",
        "org.slf4j:slf4j-api"
    ) { implementation(it) }

    Dependencies.artifacts(
        "org.junit.jupiter:junit-jupiter",
        "org.assertj:assertj-core"
    ) { testImplementation(it) }

    Dependencies.artifacts(
        "org.tinylog:slf4j-binding"
    ) { testRuntimeOnly(it) }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn("dokka")
    archiveClassifier.set("javadoc")
    from("$projectDir/${Constants.dokkaDir}")
}

publishing {
    publications {
        create<MavenPublication>(Constants.publicationName) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    dryRun = false
    publish = true
    setPublications(Constants.publicationName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = Constants.publicationName
        name = project.name
        desc = "Kotlin extension functions for SLF4J API."
        websiteUrl = "https://github.com/nwillc/slf4jkext"
        issueTrackerUrl = "https://github.com/nwillc/slf4jkext/issues"
        vcsUrl = "https://github.com/nwillc/slf4jkext.git"
        version.vcsTag = "v${project.version}"
        setLicenses("ISC")
        setLabels("kotlin", "SLF4J")
        publicDownloadNumbers = true
    })
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "failed", "skipped")
        }
    }
    named<Jar>("jar") {
        manifest.attributes["Automatic-Module-Name"] = "${project.group}.${project.name}"
    }
    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$projectDir/${Constants.dokkaDir}"
    }
    withType<BintrayUploadTask> {
        onlyIf {
            if (project.version.toString().contains('-')) {
                logger.lifecycle("Version v${project.version} is not a release version - skipping upload.")
                false
            } else {
                true
            }
        }
    }
}

ktlint {
    version.set(ToolVersions.ktlint)
    disabledRules.set(setOf("import-ordering"))
}

detekt {
    toolVersion = PluginVersions.detekt
    reports {
        html.enabled = true
        txt.enabled = true
    }
}
