dependencies {
    implementation(project(":core"))

    implementation("org.bstats:bstats-bungeecord:2.2.1")
    implementation("net.kyori:adventure-platform-bungeecord:4.0.0-SNAPSHOT")

    compileOnly("io.github.waterfallmc:waterfall-api:1.17-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("EpicGuardWaterfall-${project.version}.jar")

        relocate("net.kyori.adventure", "me.xneox.epicguard.waterfall.adventure")
        relocate("net.kyori.examination", "me.xneox.epicguard.waterfall.examination")
        relocate("org.bstats", "me.xneox.epicguard.waterfall.bstats")
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
