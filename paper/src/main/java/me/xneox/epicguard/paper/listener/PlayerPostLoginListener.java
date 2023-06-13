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

package me.xneox.epicguard.paper.listener;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PostLoginHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerPostLoginListener extends PostLoginHandler implements PaperListener<PlayerJoinEvent> {
  public PlayerPostLoginListener(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public void handle(final PlayerJoinEvent event) {
    var player = event.getPlayer();
    this.onPostLogin(player.getUniqueId(), Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress());
  }

  @Override
  public Class<PlayerJoinEvent> clazz() {
    return PlayerJoinEvent.class;
  }
}
