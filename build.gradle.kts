plugins {
    java
}

group = "net.impactdev.impactor.api"

subprojects {
    apply(plugin = "java")

    java {
        withJavadocJar()
    }
}