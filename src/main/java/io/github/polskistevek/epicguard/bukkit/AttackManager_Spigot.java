package io.github.polskistevek.epicguard.bukkit;

import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.util.MessagesBukkit;
import io.github.polskistevek.epicguard.bukkit.util.Notificator;
import io.github.polskistevek.epicguard.universal.AttackManager;
import io.github.polskistevek.epicguard.universal.AttackType;
import io.github.polskistevek.epicguard.universal.util.ChatUtil;
import io.github.polskistevek.epicguard.universal.util.KickReason;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AttackManager_Spigot {
    public static void handleDetection(String nick, String adress, String reason) {
        Notificator.title(MessagesBukkit.ATTACK_TITLE.replace("{MAX}", String.valueOf(DataFileManager.blockedBots)), MessagesBukkit.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(AttackManager.connectPerSecond)));
        Notificator.action(MessagesBukkit.ACTIONBAR_ATTACK.replace("{NICK}", nick).replace("{IP}", adress).replace("{DETECTION}", reason).replace("{CPS}", String.valueOf(AttackManager.connectPerSecond)));
        DataFileManager.blockedBots++;
    }

    public static void closeConnection(AsyncPlayerPreLoginEvent e, KickReason reason) {
        if (reason == KickReason.GEO) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_COUNTRY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.ATTACK) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_ATTACK) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.BLACKLIST) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_BLACKLIST) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.PROXY) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_PROXY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.VERIFY) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_VERIFY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }
    }

    public static void runTask(AttackType type) {
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), () -> {
            if (type == AttackType.CONNECT){
                AttackManager.joinPerSecond--;
            }
            if (type == AttackType.PING){
                AttackManager.pingPerSecond--;
            }
            if (type == AttackType.JOIN){
                AttackManager.joinPerSecond--;
            }
        }, 20L);
    }
}
