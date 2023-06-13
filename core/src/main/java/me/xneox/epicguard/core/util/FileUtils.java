/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.EpicGuardAPI;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * This util helps with managing files.
 */
public final class FileUtils {
  private FileUtils() {}
  public static final String EPICGUARD_DIR = "plugins/EpicGuard";

  public static void downloadFile(@NotNull String urlFrom, @NotNull Path file) throws IOException {
    EpicGuardAPI.INSTANCE.instance().logger().info("Downloading file from {} to {}", urlFrom, file.toAbsolutePath());

    // Make sure the original file will be replaced, if it exists
    Files.deleteIfExists(file);
    Files.createFile(file);

    var connection = URLUtils.openConnection(urlFrom);
    try (ReadableByteChannel channel = Channels.newChannel(connection.getInputStream());
        var outputStream = new FileOutputStream(file.toFile())) {

      outputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
    }
  }

  /**
   * Creates new file, catches eventual exception and logs to debug.
   *
   * @param file The file to be created.
   * @return The created file.
   */
  @NotNull
  public static Path create(@NotNull Path file) {
    Objects.requireNonNull(file, "Can't create null file!");

    try {
      if (Files.notExists(file)) {
        Files.createFile(file);
        LogUtils.debug("Created new file: " + file);
      }
    } catch (IOException e) {
      LogUtils.catchException("Can't create database file", e);
    }

    return file;
  }
}
