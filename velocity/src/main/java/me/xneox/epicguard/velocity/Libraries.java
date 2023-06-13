package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.xneox.epicguard.core.util.VersionUtils;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import net.byteflux.libby.relocation.Relocation;
import org.slf4j.Logger;

import java.nio.file.Path;

final class Libraries {
    @Inject
    @DataDirectory
    private Path folderPath;
    @Inject
    private PluginManager pluginManager;
    @Inject
    private EpicGuardVelocity plugin;
    @Inject
    private Logger logger;

    void register() {
        final var manager = new VelocityLibraryManager<>(logger, folderPath, pluginManager, plugin);

        final Library MYSQL = Library.builder()
                .groupId("com{}mysql")
                .artifactId("mysql-connector-j")
                .version("8.0.32")
                .id("mysql")
                .relocate("com{}mysql{}cj", "me{}xneox{}epicguard{}libs{}mysql")
                .build();
        final Library SQLITE = Library.builder()
                .groupId("org{}xerial")
                .artifactId("sqlite-jdbc")
                .version("3.40.1.0")
                .id("sqlite")
                .relocate("org{}xerial", "me{}xneox{}epicguard{}libs{}org{}xerial")
                .build();

        final Relocation configurateRelocation = new Relocation(
                "org{}spongepowered",
                "me{}xneox{}epicguard{}libs{}org{}spongepowered"
        );
        final Relocation geantyrefRelocation = new Relocation(
                "io{}leangen{}geantyref",
                "me{}xneox{}epicguard{}libs{}io{}leangen{}geantyref"
        );
        final Library CONFIGURATE_HOCON = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-hocon")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-hocon")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
                .build();
        final Library CONFIGURATE_CORE = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-core")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
                .build();
        final Library GEANTYREF = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
                .build();
        final Library HIKARI = Library.builder()
                .groupId("com{}zaxxer")
                .artifactId("HikariCP")
                .version(VersionUtils.HIKARI)
                .id("hikari")
                .relocate("com{}zaxxer{}hikari", "me{}xneox{}epicguard{}libs{}com{}zaxxer{}hikari")
                .build();
        final Relocation commonsRelocation = new Relocation(
                "org{}apache{}commons",
                "me{}xneox{}epicguard{}libs{}commons"
        );
        final Library COMMONS_COMPRESS = Library.builder()
                .groupId("org{}apache{}commons")
                .artifactId("commons-compress")
                .version(VersionUtils.COMMANDS_COMPRESS)
                .id("commons-compress")
                .relocate(commonsRelocation)
                .build();
        final Library COMMONS_TEXT = Library.builder()
                .groupId("org{}apache{}commons")
                .artifactId("commons-text")
                .version(VersionUtils.COMMONS_TEXT)
                .id("commons-text")
                .relocate(commonsRelocation)
                .build();
        final Library MAXMIND_GEOIP = Library.builder()
                .groupId("com.maxmind.geoip2")
                .artifactId("geoip2")
                .version(VersionUtils.GEOIP)
                .id("geoip2")
                .build();
        final Library MAXMIND_DB = Library.builder()
                .groupId("com.maxmind.db")
                .artifactId("maxmind-db")
                .version(VersionUtils.MAXMIND_DB)
                .id("maxmind-db")
                .build();
        final Library JACKSON_ANNOTATIONS = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-annotations")
                .version(VersionUtils.JACKSON)
                .id("jackson-annotations")
                .build();
        final Library JACKSON_CORE = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-core")
                .version(VersionUtils.JACKSON)
                .id("jackson-core")
                .build();
        final Library JACKSON_DATABIND = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-databind")
                .version(VersionUtils.JACKSON)
                .id("jackson-databind")
                .build();

        manager.addMavenCentral();
        manager.loadLibrary(SQLITE);
        manager.loadLibrary(MYSQL);
        manager.loadLibrary(GEANTYREF);
        manager.loadLibrary(CONFIGURATE_CORE);
        manager.loadLibrary(CONFIGURATE_HOCON);
        manager.loadLibrary(HIKARI);
        manager.loadLibrary(COMMONS_COMPRESS);
        manager.loadLibrary(COMMONS_TEXT);
        manager.loadLibrary(MAXMIND_GEOIP);
        manager.loadLibrary(MAXMIND_DB);
        manager.loadLibrary(JACKSON_ANNOTATIONS);
        manager.loadLibrary(JACKSON_CORE);
        manager.loadLibrary(JACKSON_DATABIND);
    }

}
