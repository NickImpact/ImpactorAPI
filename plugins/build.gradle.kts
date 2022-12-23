plugins {
    id("impactor.base-conventions")
    id("impactor.publishing-conventions")
}

dependencies {
    api(project(":api:core"))
    api(project(":api:config"))
    api(project(":api:storage"))
}