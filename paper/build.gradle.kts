dependencies {
    implementation(project(":core"))

    implementation("org.bstats:bstats-bukkit:2.2.1")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("EpicGuardPaper-${project.version}.jar")
        relocate("org.bstats", "me.xneox.epicguard.paper.bstats")
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }
}