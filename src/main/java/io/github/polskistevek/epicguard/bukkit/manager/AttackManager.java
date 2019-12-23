package io.github.polskistevek.epicguard.bukkit.manager;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.util.MessagesBukkit;
import io.github.polskistevek.epicguard.bukkit.util.Notificator;
import io.github.polskistevek.epicguard.utils.ChatUtil;
import io.github.polskistevek.epicguard.utils.KickReason;

import java.util.ArrayList;
import java.util.List;

public class AttackManager {
    public static int joinPerSecond = 0;
    public static int connectPerSecond = 0;
    public static int pingPerSecond = 0;
    public static boolean attackMode = false;
    public static List<String> rejoinData = new ArrayList<>();

    public enum AttackType {
        PING,
        CONNECT,
        JOIN
    }

    public static boolean checkAttackStatus(AttackType type) {
        if (type == AttackType.PING) {
            if (pingPerSecond > GuardBukkit.PING_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        if (type == AttackType.JOIN) {
            if (joinPerSecond > GuardBukkit.JOIN_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        if (type == AttackType.CONNECT) {
            if (connectPerSecond > GuardBukkit.CONNECT_SPEED) {
                attackMode = true;
                return true;
            }
            return false;
        }
        return false;
    }

    public static void handleDetection(String reason, String nick, String adress) {
        Notificator.title(MessagesBukkit.ATTACK_TITLE.replace("{MAX}", String.valueOf(DataFileManager.blockedBots)), MessagesBukkit.ATTACK_SUBTITLE.replace("{CPS}", String.valueOf(AttackManager.connectPerSecond)));
        Notificator.action(MessagesBukkit.ACTIONBAR_ATTACK.replace("{NICK}", nick).replace("{IP}", adress).replace("{DETECTION}", reason).replace("{CPS}", String.valueOf(AttackManager.connectPerSecond)));
        DataFileManager.blockedBots++;
    }

    public static void handleAttack(AttackType type) {
        if (type == AttackType.CONNECT){
            AttackManager.connectPerSecond++;
        }
        if (type == AttackType.PING){
            AttackManager.pingPerSecond++;
        }
        if (type == AttackType.JOIN){
            AttackManager.joinPerSecond++;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (type == AttackType.CONNECT){
                    AttackManager.connectPerSecond--;
                }
                if (type == AttackType.PING){
                    AttackManager.pingPerSecond--;
                }
                if (type == AttackType.JOIN){
                    AttackManager.joinPerSecond--;
                }
            }
        }.runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), 20);
    }

    public static void closeConnection(AsyncPlayerPreLoginEvent e, KickReason reason) {
        if (reason == KickReason.GEO) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_COUNTRY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.ATTACK) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_ATTACK) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.BLACKLIST) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_BLACKLIST) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.PROXY) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_PROXY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.VERIFY) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_VERIFY) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }
    }
}
