import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation(project(":core"))

    implementation("net.kyori:adventure-platform-bungeecord:4.0.0")
    compileOnly("io.github.waterfallmc:waterfall-api:1.17-R0.1-SNAPSHOT")
}

tasks.withType<ShadowJar> {
    relocate("net.kyori.adventure", "me.xneox.epicguard.waterfall.adventure")
    relocate("net.kyori.examination", "me.xneox.epicguard.waterfall.examination")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
