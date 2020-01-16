package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.task.HeuristicsTask;
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
    private static int totalBots = 0;
    private static boolean attackMode = false;

    public static int getTotalBots() {
        return totalBots;
    }

    public static void setTotalBots(int totalBots) {
        AttackManager.totalBots = totalBots;
    }

    public static boolean isUnderAttack() {
        return attackMode;
    }

    public static int getPingPerSecond() {
        return pingPerSecond;
    }

    public static int getConnectPerSecond() {
        return connectPerSecond;
    }

    public static void setConnectPerSecond(int connectPerSecond) {
        AttackManager.connectPerSecond = connectPerSecond;
    }

    public static int getJoinPerSecond() {
        return joinPerSecond;
    }

    public static void setAttackMode(boolean bol) {
        Logger.info("Attack mode changed to: " + bol);
        attackMode = bol;
    }

    public static List<String> getRejoinData() {
        return rejoinData;
    }

    public static void setRejoinData(List<String> rejoinData) {
        AttackManager.rejoinData = rejoinData;
    }

    public static void handleDetection(String reason, String nick, String adress, AsyncPlayerPreLoginEvent event, KickReason kickReason, boolean blacklist) {
        closeConnection(event, kickReason);
        Logger.debug("- " + reason + " - DETECTED & BLOCKED");
        if (blacklist) {
            BlacklistManager.add(adress);
            HeuristicsTask.setBlacklistInc(HeuristicsTask.getBlacklistInc() + 1);
            Logger.debug("- This IP has been blacklisted.");
        }
        if (getConnectPerSecond() > 5) {
            Notificator.title(MessagesBukkit.ATTACK_TITLE.replace("{CPS}", String.valueOf(getConnectPerSecond())), MessagesBukkit.ATTACK_SUBTITLE.replace("{MAX}", String.valueOf(getTotalBots())));
        }
        Notificator.action(MessagesBukkit.ACTIONBAR_ATTACK.replace("{NICK}", nick).replace("{IP}", adress).replace("{DETECTION}", reason));
        DataFileManager.blockedBots++;
        totalBots++;
    }

    public static void handleAttack(AttackType type) {
        if (type == AttackType.CONNECT) {
            connectPerSecond++;
            if (connectPerSecond > Config.connectSpeed) {
                attackMode = true;
            }
        }
        if (type == AttackType.PING) {
            pingPerSecond++;
            if (pingPerSecond > Config.pingSpeed) {
                attackMode = true;
            }
        }
        if (type == AttackType.JOIN) {
            joinPerSecond++;
            if (joinPerSecond > Config.joinSpeed) {
                attackMode = true;
            }
        }
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
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
