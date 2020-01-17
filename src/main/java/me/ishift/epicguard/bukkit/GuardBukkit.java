package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.bukkit.gui.MaterialUtil;
import me.ishift.epicguard.bukkit.listener.*;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.manager.FileManager;
import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.object.CustomFile;
import me.ishift.epicguard.bukkit.task.*;
import me.ishift.epicguard.bukkit.util.LogFilter;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.bukkit.util.MiscUtil;
import me.ishift.epicguard.bukkit.util.nms.NMSUtil;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.LogoPrinter;
import me.ishift.epicguard.universal.util.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuardBukkit extends JavaPlugin {
    public static final String PERMISSION = "epicguard.admin";

    public static GuardBukkit getInstance() {
        return JavaPlugin.getPlugin(GuardBukkit.class);
    }

    public static void loadConfig() {
        final FileConfiguration config = getInstance().getConfig();
        Config.firewallEnabled = config.getBoolean("firewall");
        Config.firewallBlacklistCommand = config.getString("firewall.command-blacklist");
        Config.firewallWhitelistCommand = config.getString("firewall.command-whitelist");
        Config.connectSpeed = config.getInt("speed.connection");
        Config.pingSpeed = config.getInt("speed.ping-speed");
        Config.autoWhitelist = config.getBoolean("auto-whitelist.enabled");
        Config.autoWhitelistTime = config.getInt("auto-whitelist.time");
        Config.antibotQuery1 = config.getString("antibot.checkers.1.adress");
        Config.antibotQuery2 = config.getString("antibot.checkers.2.adress");
        Config.antibotQuery3 = config.getString("antibot.checkers.3.adress");
        Config.antibotQueryContains = config.getStringList("antibot.checkers.responses");
        Config.countryList = config.getStringList("countries.list");
        Config.countryMode = config.getString("countries.mode");
        Config.antibot = config.getBoolean("antibot.enabled");
        Config.updater = config.getBoolean("updater");
        Config.attackResetTimer = config.getLong("speed.attack-timer-reset");
        Config.joinSpeed = config.getInt("speed.join-speed");
        Config.tabCompleteBlock = config.getBoolean("fully-block-tab-complete");
        Config.blockedCommands = config.getStringList("command-protection.list");
        Config.allowedCommands = config.getStringList("allowed-commands.list");
        Config.opProtectionList = config.getStringList("op-protection.list");
        Config.opProtectionAlert = config.getString("op-protection.alert");
        Config.opProtectionCommand = config.getString("op-protection.command");
        Config.allowedCommandsBypass = config.getString("allowed-commands.bypass");
        Config.blockedCommandsEnable = config.getBoolean("command-protection.enabled");
        Config.allowedCommandsEnable = config.getBoolean("allowed-commands.enabled");
        Config.opProtectionEnable = config.getBoolean("op-protection.enabled");
        Config.ipHistoryEnable = config.getBoolean("ip-history.enabled");
        Config.forceRejoin = config.getBoolean("antibot.force-rejoin");
        Config.pexProtection = config.getBoolean("op-protection.pex-protection");
        Config.blockedNames = config.getStringList("antibot.name-contains");

        final String path = getInstance().getDataFolder() + "/cloud.yml";
        FileManager.createFile(path);
        final CustomFile cloudFile = FileManager.getFile(path);
        if (!cloudFile.isExisting()) {
            cloudFile.create();
            cloudFile.getConfig().set("cloud.enabled", true);
            cloudFile.getConfig().set("cloud.sync-every-seconds", 1800);
            cloudFile.getConfig().set("cloud.features.blacklist", true);
            cloudFile.getConfig().set("heuristics.enabled", true);
            cloudFile.getConfig().set("heuristics.min-difference", 7);
            cloudFile.save();
        }

        Config.cloudEnabled = cloudFile.getConfig().getBoolean("cloud.enabled");
        Config.cloudBlacklist = cloudFile.getConfig().getBoolean("cloud.features.blacklist");
        Config.cloudTime = cloudFile.getConfig().getLong("cloud.sync-every-seconds");
        Config.heuristicsEnabled = cloudFile.getConfig().getBoolean("heuristics.enabled");
        Config.heuristicsDiff = cloudFile.getConfig().getInt("heuristics.min-difference");
    }

    @Override
    public void onEnable() {
        try {
            final long ms = System.currentTimeMillis();
            this.saveDefaultConfig();
            this.createDirectories();
            new Logger(ServerType.SPIGOT);
            loadConfig();
            LogoPrinter.print();
            new GeoAPI(ServerType.SPIGOT);
            new Metrics(this);

            NMSUtil.init();
            DataFileManager.init(this.getDataFolder() + "/data/data_flat.yml");
            DataFileManager.save();
            MessagesBukkit.load();

            this.registerListeners();
            this.registerTasks();

            MaterialUtil.init();
            GuiMain.eq = Bukkit.createInventory(null, 27, "EpicGuard Management Menu");
            GuiPlayers.inv = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

            this.getCommand("epicguard").setExecutor(new GuardCommand());
            this.getCommand("epicguard").setTabCompleter(new GuardTabCompleter());
            this.registerBrand();

            new LogFilter(this.getDataFolder() + "/filter.yml").registerFilter();

            Bukkit.getOnlinePlayers().forEach(UserManager::addUser);
            Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            Logger.info("Saving data and disabling plugin.");
            DataFileManager.save();
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    private void registerListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        if (pm.isPluginEnabled("ProtocolLib")) {
            MiscUtil.registerProtocolLib(this);
        }
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new InventoryTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new HeuristicsTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTitleTask(), 1L, 220L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 1L, Config.attackResetTimer);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CloudTask(), 40L, Config.cloudTime * 20);
    }

    private void createDirectories() {
        File cfg = new File(this.getDataFolder() + "/config.yml");
        File dir1 = new File(this.getDataFolder() + "/logs");
        if (!dir1.exists()) {
            dir1.mkdir();
        }
        File dir2 = new File(this.getDataFolder() + "/deprecated");
        if (!dir2.exists()) {
            cfg.renameTo(new File(dir2 + "/config.yml"));
            dir2.mkdir();
        }
        File dir3 = new File(this.getDataFolder() + "/data");
        if (!dir3.exists()) {
            dir3.mkdir();
        }
    }

    private void registerBrand() {
        FileManager.createFile(this.getDataFolder() + "/brand.yml");
        final CustomFile brandConfig = FileManager.getFile(this.getDataFolder() + "/brand.yml");
        if (!brandConfig.isExisting()) {
            List<String> blockedBrandDefault = new ArrayList<>();
            blockedBrandDefault.add("some_blocked_brand");
            brandConfig.getConfig().set("channel-verification.enabled", true);
            brandConfig.getConfig().set("channel-verification.punish", "kick {PLAYER} &cException occurred in your connection, please rejoin!");
            brandConfig.getConfig().set("blocked-brands.enabled", true);
            brandConfig.getConfig().set("blocked-brands.punish", "kick {PLAYER} &cYour client is not allowed on this server!");
            brandConfig.getConfig().set("blocked-brands.list", blockedBrandDefault);
            brandConfig.save();
        }

        if (NMSUtil.isOldVersion()) {
            final Messenger messenger = Bukkit.getMessenger();
            messenger.registerIncomingPluginChannel(this, "MC|Brand", new BrandPluginMessageListener());
        }
    }
}
