dependencies {
    implementation(project(":core"))

    implementation("org.bstats:bstats-bungeecord:2.2.1")
    implementation("net.kyori:adventure-platform-bungeecord:4.0.0-SNAPSHOT")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3") // MySQL driver is present on bungee, but SQLite is not.
    
    compileOnly("io.github.waterfallmc:waterfall-api:1.17-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("EpicGuardWaterfall-${project.version}.jar")

        relocate("net.kyori.adventure", "me.xneox.epicguard.bungee.adventure")
        relocate("net.kyori.examination", "me.xneox.epicguard.bungee.examination")
        relocate("org.bstats", "me.xneox.epicguard.bungee.bstats")
    }

    processResources {
        filesMatching("bungee.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
