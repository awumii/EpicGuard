package me.xneox.epicguard.core.check.impl;

import com.google.common.collect.EvictingQueue;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.check.Check;
import me.xneox.epicguard.core.user.PendingUser;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javax.annotation.Nonnull;
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
    public boolean handle(@Nonnull PendingUser user) {
        for (String nick : this.nameHistory) {
            if (this.distanceAlgorithm.apply(nick, user.nickname()) <= this.epicGuard.config().nameSimilarityCheck().distance()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nonnull List<String> kickMessage() {
        return this.epicGuard.messages().disconnect().nameSimilarity();
    }
}
