plugins {
    id("impactor.api-conventions")
}

dependencies {
    api(project(":api:core"))
    api(project(":api:players"))
    api(project(":api:text"))

    compileOnly(fileTree("dir" to "libs", "include" to "*.jar"))
}