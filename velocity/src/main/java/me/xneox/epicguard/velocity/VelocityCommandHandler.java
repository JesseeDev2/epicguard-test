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

package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.SimpleCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandHandler;

public final class VelocityCommandHandler extends CommandHandler implements SimpleCommand {
  @Inject
  private CommandManager commandManager;
  @Inject
  private EpicGuardVelocity plugin;
  @Inject
  public VelocityCommandHandler(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public void execute(Invocation invocation) {
    this.handleCommand(invocation.arguments(), invocation.source());
  }

  @Override
  public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
    return CompletableFuture.supplyAsync(() ->
      new ArrayList<>(this.handleSuggestions(invocation.arguments())));
  }

  @Override
  public boolean hasPermission(Invocation invocation) {
    return invocation.source().hasPermission("epicguard.admin");
  }

  void register() {
    final var meta = commandManager
            .metaBuilder("epicguard")
            .aliases("guard", "epicguardvelocity", "guardvelocity")
            .plugin(plugin)
            .build();
    commandManager.register(meta, this);
  }
}
