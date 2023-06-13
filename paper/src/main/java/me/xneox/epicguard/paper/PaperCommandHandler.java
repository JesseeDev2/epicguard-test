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

package me.xneox.epicguard.paper;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class PaperCommandHandler extends Command implements PluginIdentifiableCommand {
  private final CommandHandler HANDLER;
  private final EpicGuardPaper plugin;
  public PaperCommandHandler(EpicGuard epicGuard, EpicGuardPaper plugin) {
    super("epicguard", "Main plugin command.", "",
            List.of("guard", "epicguardpaper", "guardpaper"));
    this.setPermission("epicguard.admin");
    this.plugin = plugin;
    this.HANDLER = new CommandHandler(epicGuard);
  }

  private static final String[] EMPTY_ARRAY = new String[0];

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
    HANDLER.handleCommand(args, sender);
    return false;
  }

  @Override
  public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
    if (args.length == 0) {
      return new ArrayList<>(HANDLER.handleSuggestions(EMPTY_ARRAY));
    }

    final String command = args[0].toLowerCase(Locale.ROOT);

    if (command.equals("guard") || command.equals("epicguardpaper") || command.equals("guardpaper")) {
      return new ArrayList<>(HANDLER.handleSuggestions(Arrays.copyOfRange(args, 1, args.length)));
    }
    return List.of();
  }

  @Override
  public @NotNull Plugin getPlugin() {
    return this.plugin;
  }
}
