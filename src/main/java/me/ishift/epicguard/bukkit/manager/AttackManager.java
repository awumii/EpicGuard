package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class AttackManager {
    public static List<String> rejoinData = new ArrayList<>();
    private static int joinPerSecond = 0;
    private static int connectPerSecond = 0;
    private static int pingPerSecond = 0;
    private static boolean attackMode = false;

    public static boolean isUnderAttack() {
        return attackMode;
    }

    public static int getPingPerSecond() {
        return pingPerSecond;
    }

    public static int getConnectPerSecond() {
        return connectPerSecond;
    }

    public static int getJoinPerSecond() {
        return joinPerSecond;
    }

    public static void setAttackMode(boolean attackMode) {
        AttackManager.attackMode = attackMode;
    }

    public static List<String> getRejoinData() {
        return rejoinData;
    }

    public static void setRejoinData(List<String> rejoinData) {
        AttackManager.rejoinData = rejoinData;
    }

    public static void handleDetection(String reason, String nick, String adress, AsyncPlayerPreLoginEvent event, KickReason kickReason, boolean blacklist) {
        if (blacklist) {
            BlacklistManager.add(adress);
        }
        closeConnection(event, kickReason);
        Logger.debug("- " + reason + " - FAILED");
        Notificator.title(MessagesBukkit.ATTACK_TITLE.replace("{CPS}", String.valueOf(getConnectPerSecond())), MessagesBukkit.ATTACK_SUBTITLE.replace("{MAX}", String.valueOf(DataFileManager.getBlockedBots())));
        Notificator.action(MessagesBukkit.ACTIONBAR_ATTACK.replace("{NICK}", nick).replace("{IP}", adress).replace("{DETECTION}", reason));
        DataFileManager.blockedBots++;
    }

    public static void handleAttack(AttackType type) {
        if (type == AttackType.CONNECT) {
            connectPerSecond++;
            if (connectPerSecond > Config.CONNECT_SPEED) {
                attackMode = true;
            }
        }
        if (type == AttackType.PING) {
            pingPerSecond++;
            if (pingPerSecond > Config.PING_SPEED) {
                attackMode = true;
            }
        }
        if (type == AttackType.JOIN) {
            joinPerSecond++;
            if (joinPerSecond > Config.JOIN_SPEED) {
                attackMode = true;
            }
        }
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), () -> {
            if (type == AttackType.CONNECT) {
                connectPerSecond--;
            }
            if (type == AttackType.PING) {
                pingPerSecond--;
            }
            if (type == AttackType.JOIN) {
                joinPerSecond--;
            }
        }, 20L);
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

        if (reason == KickReason.NAMECONTAINS) {
            final StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_NAMECONTAINS) {
                sb.append(ChatUtil.fix(s)).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }
    }
}
