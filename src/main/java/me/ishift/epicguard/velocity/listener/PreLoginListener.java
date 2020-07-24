package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.CheckResult;
import me.ishift.epicguard.core.handler.DetectionHandler;
import me.ishift.epicguard.velocity.util.VelocityUtils;
import net.kyori.text.Component;

public class PreLoginListener extends DetectionHandler {
    public PreLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        String address = event.getConnection().getRemoteAddress().getAddress().getHostAddress();
        String nickname = event.getUsername();

        CheckResult result = this.handle(address, nickname);
        if (result.isDetected()) {
            Component reason = VelocityUtils.getTextComponent(result.getKickMessage());
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(reason));
        }
    }
}
