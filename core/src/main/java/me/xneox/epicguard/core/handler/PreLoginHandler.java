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

package me.xneox.epicguard.core.handler;

import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.impl.AccountLimitCheck;
import me.xneox.epicguard.core.check.impl.BlacklistCheck;
import me.xneox.epicguard.core.check.impl.GeographicalCheck;
import me.xneox.epicguard.core.check.impl.LockdownCheck;
import me.xneox.epicguard.core.check.impl.NameSimilarityCheck;
import me.xneox.epicguard.core.check.impl.NicknameCheck;
import me.xneox.epicguard.core.check.impl.ProxyCheck;
import me.xneox.epicguard.core.check.impl.ReconnectCheck;
import me.xneox.epicguard.core.check.impl.ServerListCheck;
import me.xneox.epicguard.core.user.ConnectingUser;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Handler for PreLogin listeners. It performs every antibot check (except SettingsCheck).
 */
public abstract class PreLoginHandler {
  private final Set<Check> pipeline = new TreeSet<>();
  private final EpicGuard epicGuard;

  public PreLoginHandler(EpicGuard epicGuard) {
    this.epicGuard = epicGuard;

    // This will be automatically sorted based on the configured priority.
    pipeline.add(new LockdownCheck(epicGuard));
    pipeline.add(new BlacklistCheck(epicGuard));
    pipeline.add(new NicknameCheck(epicGuard));
    pipeline.add(new GeographicalCheck(epicGuard));
    pipeline.add(new ServerListCheck(epicGuard));
    pipeline.add(new ReconnectCheck(epicGuard));
    pipeline.add(new AccountLimitCheck(epicGuard));
    pipeline.add(new NameSimilarityCheck(epicGuard));
    pipeline.add(new ProxyCheck(epicGuard));

    epicGuard.logger().info("Order of the detection pipeline: " +
        String.join(", ", this.pipeline.stream().map(check -> check.getClass().getSimpleName()).toList()));
  }

  /**
   * Handling the incoming connection, and returning an optional disconnect message.
   *
   * @param address Address of the connecting user.
   * @param nickname Nickname of the connecting user.
   * @return Disconnect message, or an empty Optional if undetected.
   */
  @NotNull
  public Optional<TextComponent> handle(@NotNull String address, @NotNull String nickname) {
    // Increment the connections per second and check if it's bigger than max-cps in config.
    if (this.epicGuard.attackManager().incrementConnectionCounter()
        >= this.epicGuard.config().misc().attackConnectionThreshold()) {
      this.epicGuard.attackManager().attack(true); // If yes, then activate the attack mode.
    }

    // this is also a workaround for an issue with players connecting using GeyserMC. Proper fix needed.
    // noinspection UnstableApiUsage
    if (!InetAddresses.isInetAddress(address)) {
      this.epicGuard.logger().warn("Skipping checks for [" + address + "/" + nickname + "]: invalid address.");
      return Optional.empty();
    }

    // Check if the user is whitelisted, if yes, return empty result (undetected).
    if (this.epicGuard.storageManager().addressMeta(address).whitelisted()) {
      return Optional.empty();
    }

    // Performing all checks, we are using PendingUser
    ConnectingUser user = new ConnectingUser(address, nickname);
    for (Check check : this.pipeline) {
      if (check.isDetected(user)) {
        // debug info
        if (this.epicGuard.config().misc().debug()) {
          this.epicGuard.logger().info("(Debug) " + nickname + "/" + address + " detected by " + check.getClass().getSimpleName());
        }

        // Positive detection, kicking the player!
        return Optional.of(check.detectionMessage());
      }
    }

    // Checks finished with no detection, the player is considered legitimate and is allowed to join the server
    // Also we update his account nickname history.
    this.epicGuard.storageManager().updateAccounts(user);
    return Optional.empty();
  }
}
