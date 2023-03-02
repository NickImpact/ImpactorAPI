plugins {
    id("impactor.base-conventions")
    id("impactor.publishing-conventions")
}

dependencies {
    api(project(":api:core"))
    api(project(":api:players"))

    // Cloud Command Framework
    api("cloud.commandframework:cloud-core:1.6.2")
    api("cloud.commandframework:cloud-annotations:1.6.2")
    api("cloud.commandframework:cloud-brigadier:1.6.2")
    implementation("com.mojang:brigadier:1.0.18")
}