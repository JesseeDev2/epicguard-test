plugins {
    alias(libs.plugins.runwaterfall)
    alias(libs.plugins.pluginyml.bungee)
}

dependencies {
    implementation(projects.core)
    compileOnly(libs.adventure.platform.bungeecord)
    compileOnly(libs.waterfall)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    runWaterfall {
        waterfallVersion(libs.versions.waterfall.get().substringBefore('-'))
    }
}

bungee {
    main = "me.xneox.epicguard.waterfall.EpicGuardWaterfall"
    description = project.description as String
    name = "EpicGuard"
    version = project.version as String
    author = "neox, 4drian3d"
    libraries = listOf(
        libs.adventure.platform.bungeecord to libs.versions.adventure.platform,
        libs.sqlite to libs.versions.sqlite,
        libs.caffeine to libs.versions.caffeine,
        libs.hikaricp to libs.versions.hikaricp,
        libs.configurate to libs.versions.configurate,
        libs.commons.compress to libs.versions.commons.compress,
        libs.commons.text to libs.versions.commons.text,
        libs.geoip to libs.versions.geoip,
        libs.maxmind.db to libs.versions.maxmind.db,
        libs.jackson.core to libs.versions.jackson,
        libs.jackson.databind to libs.versions.jackson,
        libs.jackson.annotations to libs.versions.jackson,
    ).map { "${it.first.get().module}:${it.second.get()}" }
}

