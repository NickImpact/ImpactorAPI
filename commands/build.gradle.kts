plugins {
    id("impactor.base-conventions")
    id("impactor.publishing-conventions")
}

dependencies {
    api(project(":api:core"))
    api(project(":api:players"))

    // Cloud Command Framework
    api("cloud.commandframework:cloud-core:1.7.1")
    api("cloud.commandframework:cloud-annotations:1.7.1")
    api("cloud.commandframework:cloud-brigadier:1.7.1")
    implementation("com.mojang:brigadier:1.0.18")
}