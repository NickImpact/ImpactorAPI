plugins {
    id("impactor.base-conventions")
    id("impactor.publishing-conventions")
}

dependencies {
    // Impact Dev Modules
    api("net.impactdev:json:1.0.0")

    // Adventure
    api("net.kyori:adventure-api:4.13.0")
    api("net.kyori:adventure-nbt:4.13.0")
    api("net.kyori:adventure-text-serializer-plain:4.13.0")
    api("net.kyori:adventure-text-serializer-legacy:4.13.0")
    api("net.kyori:adventure-text-serializer-gson:4.13.0")
    api("net.kyori:adventure-text-minimessage:4.13.0")
    api("net.kyori:adventure-text-logger-slf4j:4.13.0")

    // Google
    api("com.google.guava:guava:31.1-jre")
    api("com.google.code.gson:gson:2.9.1")

    // Kyori Events
    api("net.kyori:event-api:5.0.0-SNAPSHOT")

    // Configurate
    api("org.spongepowered:configurate-core:4.1.2")
    api("org.spongepowered:configurate-gson:4.1.2")
    api("org.spongepowered:configurate-hocon:4.1.2")
    api("org.spongepowered:configurate-yaml:4.1.2")

    // Databases
    api("com.zaxxer:HikariCP:4.0.3")
    api("com.h2database:h2:2.1.214")
    api("mysql:mysql-connector-java:8.0.28")
    api("org.mariadb.jdbc:mariadb-java-client:2.7.2")
    api("org.mongodb:mongo-java-driver:3.12.2")

    // Misc
    api("io.leangen.geantyref:geantyref:1.3.11")
    api(group = "org.spongepowered", name = "math", version = "2.0.1")
    api("org.apache.logging.log4j:log4j-api:2.17.1")
    api("org.apache.maven:maven-artifact:3.8.7")
}