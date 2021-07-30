package me.xneox.epicguard.core.check.impl;

import com.google.common.collect.EvictingQueue;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.check.CheckMode;
import me.xneox.epicguard.core.user.ConnectingUser;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;

@SuppressWarnings("UnstableApiUsage")
public class NameSimilarityCheck extends Check {
    private final Queue<String> nameHistory = EvictingQueue.create(this.epicGuard.config().nameSimilarityCheck().historySize());
    private final LevenshteinDistance distanceAlgorithm = LevenshteinDistance.getDefaultInstance();

    public NameSimilarityCheck(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean handle(@NotNull ConnectingUser user) {
        CheckMode mode = CheckMode.valueOf(this.epicGuard.config().nameSimilarityCheck().checkMode());

        for (String nick : this.nameHistory) {
            if (nick.equals(user.nickname())) {
                return false; // ignore identical nickname.
            }

            int distance = this.distanceAlgorithm.apply(nick, user.nickname());
            if (distance <= this.epicGuard.config().nameSimilarityCheck().distance()) {
                return this.evaluate(mode, true);
            }
        }

        this.nameHistory.add(user.nickname());
        return false;
    }

    @Override
    public @NotNull List<String> kickMessage() {
        return this.epicGuard.messages().disconnect().nameSimilarity();
    }
}
