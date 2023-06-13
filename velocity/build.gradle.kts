plugins {
    alias(libs.plugins.runvelocity)
}

dependencies {
    implementation(projects.core)
    implementation(libs.libby.velocity)

    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks {
    shadowJar {
        relocate("net.byteflux.libby", "me.xneox.epicguard.velocity.libby")
        relocate("org.spongepowered", "me.xneox.epicguard.libs.org.spongepowered")
        relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.io.leangen.geantyref")
        relocate("com.zaxxer.hikari", "me.xneox.epicguard.libs.com.zaxxer.hikari")
        relocate("com.mysql.cj", "me.xneox.epicguard.libs.mysql")
        relocate("org.xerial", "me.xneox.epicguard.libs.org.xerial")
        relocate("org.apache.commons", "me.xneox.epicguard.libs.commons")
    }
    build {
        dependsOn(shadowJar)
    }
    runVelocity {
        velocityVersion(libs.versions.velocity.get())
    }
}
