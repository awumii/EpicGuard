dependencies {
    implementation(project(":core"))

    // TODO: Download these on runtime.
    implementation("mysql:mysql-connector-java:8.0.27")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")

    compileOnly("com.velocitypowered:velocity-api:3.0.0")
    annotationProcessor("com.velocitypowered:velocity-api:3.0.0")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
