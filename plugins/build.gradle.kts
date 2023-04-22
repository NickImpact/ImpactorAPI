plugins {
    id("impactor.api-conventions")
}

dependencies {
    api(project(":api:core"))
    api(project(":api:config"))
    api(project(":api:storage"))
}