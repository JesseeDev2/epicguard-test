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

package me.xneox.epicguard.velocity.listener;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.DisconnectHandler;
import me.xneox.epicguard.velocity.EpicGuardVelocity;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DisconnectListener extends DisconnectHandler implements Listener<DisconnectEvent> {
  @Inject
  private EventManager eventManager;
  @Inject
  private EpicGuardVelocity plugin;
  @Inject
  public DisconnectListener(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public void register() {
    eventManager.register(plugin, DisconnectEvent.class, this);
  }

  @Override
  public @Nullable EventTask executeAsync(final DisconnectEvent event) {
    if (event.getLoginStatus() == DisconnectEvent.LoginStatus.CONFLICTING_LOGIN) {
      return null;
    }

    return EventTask.async(() -> {
      var player = event.getPlayer();
      this.onDisconnect(player.getUniqueId());
    });
  }
}
