dependencies {
    implementation(project(":core"))

    implementation("org.bstats:bstats-bukkit:2.2.1")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
