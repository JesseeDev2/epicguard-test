package me.xneox.epicguard.paper;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import me.xneox.epicguard.core.util.VersionUtils;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class LibraryLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        final MavenLibraryResolver resolver = new MavenLibraryResolver();

        final RemoteRepository mavenCentral = new RemoteRepository
                .Builder("central", "default", "https://repo1.maven.org/maven2/")
                .build();
        resolver.addRepository(mavenCentral);

        Stream.of(
                "org.spongepowered:configurate-hocon:" + VersionUtils.CONFIGURATE,
                "com.zaxxer:HikariCP:" + VersionUtils.HIKARI,
                "com.github.ben-manes.caffeine:caffeine:" + VersionUtils.CAFFEINE,
                "org.apache.commons:commons-compress:" + VersionUtils.COMMANDS_COMPRESS,
                "org.apache.commons:commons-text:" + VersionUtils.COMMONS_TEXT,
                "com.maxmind.geoip2:geoip2:" + VersionUtils.GEOIP,
                "com.maxmind.db:maxmind-db:" + VersionUtils.MAXMIND_DB,
                "com.fasterxml.jackson.core:jackson-annotations:" + VersionUtils.JACKSON,
                "com.fasterxml.jackson.core:jackson-core:" + VersionUtils.JACKSON,
                "com.fasterxml.jackson.core:jackson-databind:" + VersionUtils.JACKSON
        )
                .map(artifact -> new Dependency(new DefaultArtifact(artifact), null))
                .forEach(resolver::addDependency);

        classpathBuilder.addLibrary(resolver);
    }
}
