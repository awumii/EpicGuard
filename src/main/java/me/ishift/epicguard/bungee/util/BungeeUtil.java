package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.util.ChatUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeUtil {
    public static void sendTitle(ProxiedPlayer player, String title, String subtitle) {
        final Title titleBuilder = GuardBungee.plugin.getProxy().createTitle()
                .title(new TextComponent(ChatUtil.fix(title)))
                .subTitle(new TextComponent(ChatUtil.fix(subtitle)))
                .fadeIn(0)
                .fadeOut(10)
                .stay(10);
        player.sendTitle(titleBuilder);
    }

    public static void sendActionBar(ProxiedPlayer player, String message) {
        player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fix(message)));
    }
}
