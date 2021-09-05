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

package me.xneox.epicguard.core.check.impl;

import com.google.common.collect.EvictingQueue;
import java.util.List;
import java.util.Queue;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.user.ConnectingUser;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class NameSimilarityCheck extends Check {
  private final Queue<String> nameHistory = EvictingQueue.create(this.epicGuard.config().nameSimilarityCheck().historySize());
  private final LevenshteinDistance distanceAlgorithm = LevenshteinDistance.getDefaultInstance();

  public NameSimilarityCheck(EpicGuard epicGuard) {
    super(epicGuard);
  }

  @Override
  public boolean handle(@NotNull ConnectingUser user) {
    synchronized (this.nameHistory) {
      for (String nick : this.nameHistory) {
        if (nick.equals(user.nickname())) {
          return false; // ignore identical nickname.
        }

        int distance = this.distanceAlgorithm.apply(nick, user.nickname());
        if (distance <= this.epicGuard.config().nameSimilarityCheck().distance()) {
          return this.evaluate(this.epicGuard.config().nameSimilarityCheck().checkMode(), true);
        }
      }

      this.nameHistory.add(user.nickname());
      return false;
    }
  }

  @Override
  public @NotNull List<String> kickMessage() {
    return this.epicGuard.messages().disconnect().nameSimilarity();
  }
}
