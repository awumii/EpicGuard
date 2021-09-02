plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

allprojects {
    group = "me.xneox"
    version = "7.1.0"
}

subprojects {
    plugins.apply("java")
    plugins.apply("com.github.johnrengelman.shadow")

    tasks {
        shadowJar {
            relocate("org.spongepowered.configurate", "me.xneox.epicguard.libs.configurate")
            relocate("org.apache.commons", "me.xneox.epicguard.libs.apachecommons")
            relocate("com.fasterxml", "me.xneox.epicguard.libs.fasterxml")
            relocate("com.maxmind", "me.xneox.epicguard.libs.maxmind")
            relocate("com.google.common", "me.xneox.epicguard.libs.googlecommons")
            relocate("com.typesafe.config", "me.xneox.epicguard.libs.config")
            relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.geantyref")
            relocate("com.zaxxer.hikari", "me.xneox.epicguard.libs.hikari")
            relocate("co.aikar.idb", "me.xneox.epicguard.libs.idb")

            // Exclude useless text files
            exclude("mozilla/")
            exclude("org/apache/commons/codec/")

            // Minimize, but exclude drivers shaded in the velocity platform
            minimize {
                exclude(dependency("mysql:.*:.*"))
                exclude(dependency("org.xerial:sqlite-jdbc:.*"))
            }

            // Copy compiled platform jars to '/out' directory for convenience.
            doLast {
                // Do not copy the core jar.
                if (!archiveFile.get().asFile.name.startsWith("core")) {
                    copy {
                        from(archiveFile)
                        into("${rootProject.projectDir}/out")
                    }
                }
            }
        }

        build {
            dependsOn(shadowJar)
        }
    }
}
