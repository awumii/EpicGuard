import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }
}

subprojects {
    tasks.withType<ShadowJar> {
        val platformName = project.name.capitalize()
        archiveFileName.set("EpicGuard$platformName-${project.version}.jar")

        relocate("org.bstats", "me.xneox.epicguard.${project.name}.bstats")
        relocate("org.spongepowered.configurate", "me.xneox.epicguard.libs.configurate")
        relocate("org.apache.commons", "me.xneox.epicguard.libs.apachecommons")
        relocate("com.fasterxml", "me.xneox.epicguard.libs.fasterxml")
        relocate("com.maxmind", "me.xneox.epicguard.libs.maxmind")
        relocate("com.google.common", "me.xneox.epicguard.libs.googlecommons")
        relocate("com.typesafe.config", "me.xneox.epicguard.libs.config")
        relocate("com.zaxxer.hikari", "me.xneox.epicguard.libs.hikari")
        relocate("co.aikar.idb", "me.xneox.epicguard.libs.idb")
        relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.geantyref")

        // Minimize, but exclude drivers shaded in the velocity platform.
        minimize {
            exclude(dependency("mysql:.*:.*"))
            exclude(dependency("org.xerial:sqlite-jdbc:.*"))
        }

        // Copy compiled platform jars to '/build' directory for convenience.
        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(16)
    }

    // For Waterfall and Paper platforms: set version
    tasks.withType<ProcessResources> {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}
