rootProject.name = "EpicGuard"

dependencyResolutionManagement {
    repositories {
        maven("https://nexus.velocitypowered.com/repository/maven-public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.aikar.co/content/groups/aikar/")
        mavenCentral()
    }
}

include("core", "paper", "velocity", "waterfall")