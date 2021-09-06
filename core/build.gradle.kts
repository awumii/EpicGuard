plugins {
    `maven-publish`
    id("net.kyori.blossom") version "1.3.0"
}

dependencies {
    implementation("com.maxmind.geoip2:geoip2:2.15.0")
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("org.jetbrains:annotations:22.0.0")
    implementation("com.zaxxer:HikariCP:5.0.0")
    implementation("co.aikar:idb-core:1.0.0-SNAPSHOT")

    compileOnly("net.kyori:adventure-api:4.9.0")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.8.1")
    compileOnly("org.apache.logging.log4j:log4j-core:2.14.1")
    compileOnly("org.slf4j:slf4j-api:1.7.32")
}

blossom {
    replaceToken("{version}", project.version, "src/main/java/me/xneox/epicguard/core/util/VersionUtils.java")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "EpicGuard"

            from(components["java"])
        }
    }
}