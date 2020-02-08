package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.task.HeuristicsTask;
import me.ishift.epicguard.bukkit.util.misc.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.KickReason;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            BlacklistManager.blacklist(adress);
            HeuristicsTask.setBlacklistInc(HeuristicsTask.getBlacklistInc() + 1);
            Logger.debug("- This IP has been blacklisted.");
        }
        PlayerPreLoginListener.setLastPlayer(nick);
        PlayerPreLoginListener.setLastAddress(adress);
        try {
            PlayerPreLoginListener.setLastCountry(GeoAPI.getCountryCode(InetAddress.getByName(adress)));
        } catch (UnknownHostException ignored) {
        }
        PlayerPreLoginListener.setLastDetection(reason);
        PlayerPreLoginListener.setBlacklisted(blacklist);
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
        String kickReason = "";
        if (reason == KickReason.GEO) {
            kickReason = MessagesBukkit.MESSAGE_KICK_COUNTRY.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }

        if (reason == KickReason.ATTACK) {
            kickReason = MessagesBukkit.MESSAGE_KICK_ATTACK.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }

        if (reason == KickReason.BLACKLIST) {
            kickReason = MessagesBukkit.MESSAGE_KICK_BLACKLIST.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }

        if (reason == KickReason.PROXY) {
            kickReason = MessagesBukkit.MESSAGE_KICK_PROXY.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }

        if (reason == KickReason.VERIFY) {
            kickReason = MessagesBukkit.MESSAGE_KICK_VERIFY.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }

        if (reason == KickReason.NAMECONTAINS) {
            kickReason = MessagesBukkit.MESSAGE_KICK_NAMECONTAINS.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
        }
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, kickReason);
    }
}
