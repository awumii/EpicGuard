package me.ishift.epicguard.bungee;

import me.ishift.epicguard.core.util.ChatUtils;
import me.ishift.epicguard.core.util.Notificator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public class BungeeNotificator implements Notificator {
    @Override
    public void sendActionBar(String message, UUID target) {
        ProxyServer.getInstance().getPlayer(target).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.colored(message)));
    }
}
