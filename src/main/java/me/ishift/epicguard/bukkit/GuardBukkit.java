package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.bukkit.listener.player.*;
import me.ishift.epicguard.bukkit.listener.server.PluginMessagesListener;
import me.ishift.epicguard.bukkit.listener.server.ServerListPingListener;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.task.*;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.bukkit.util.misc.Metrics;
import me.ishift.epicguard.bukkit.util.server.LogFilter;
import me.ishift.epicguard.bukkit.util.misc.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.server.Hooks;
import me.ishift.epicguard.bukkit.util.server.Reflection;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.ServerType;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import me.ishift.epicguard.universal.util.LogoPrinter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.io.File;

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
        Config.apiKey = config.getString("antibot.api-key");
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
        Config.cloudEnabled = config.getBoolean("cloud.enabled");
        Config.cloudBlacklist = config.getBoolean("cloud.features.blacklist");
        Config.cloudTime = config.getLong("cloud.sync-every-seconds");
        Config.heuristicsEnabled = config.getBoolean("heuristics.enabled");
        Config.heuristicsDiff = config.getInt("heuristics.min-difference");
        Config.filterEnabled = config.getBoolean("console-filter.enabled");
        Config.filterValues = config.getStringList("console-filter.messages");
        Config.bandwidthOptimizer = config.getBoolean("bandwidth-optimizer");
        Config.customTabComplete = config.getBoolean("custom-tab-complete.enabled");
        Config.customTabCompleteList = config.getStringList("custom-tab-complete.list");
    }

    @Override
    public void onEnable() {
        final long ms = System.currentTimeMillis();
        this.saveDefaultConfig();
        this.createDirectories();
        this.saveDefaultConfig();
        loadConfig();
        Logger.create(ServerType.SPIGOT);
        LogoPrinter.print();
        Logger.info("Version: " + this.getDescription().getVersion());
        GeoAPI.create(ServerType.SPIGOT);
        new Metrics(this, 5845);

        Reflection.init();
        DataFileManager.init(this.getDataFolder() + "/data/data_flat.yml");
        DataFileManager.save();
        MessagesBukkit.load();

        this.registerListeners();
        this.registerTasks();

        GuiMain.eq = Bukkit.createInventory(null, 27, "EpicGuard Management Menu");
        GuiPlayers.inv = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

        this.getCommand("epicguard").setExecutor(new GuardCommand());
        this.getCommand("epicguard").setTabCompleter(new GuardTabCompleter());

        if (Reflection.isOldVersion()) {
            final Messenger messenger = Bukkit.getMessenger();
            messenger.registerIncomingPluginChannel(this, "MC|Brand", new PluginMessagesListener());
        }

        new LogFilter().registerFilter();

        Updater.checkForUpdates();
        Bukkit.getOnlinePlayers().forEach(UserManager::addUser);
        Logger.info("Succesfully loaded! Took: " + (System.currentTimeMillis() - ms) + "ms");
    }

    @Override
    public void onDisable() {
        Logger.info("Saving data and disabling plugin.");
        DataFileManager.save();
    }

    private void registerListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            Hooks.registerProtocolLib(this);
        }
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new InventoryTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new HeuristicsTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTitleTask(), 1L, 220L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 1L, Config.attackResetTimer);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new CloudTask(), 40L, Config.cloudTime);
    }

    private void createDirectories() {
        // I don't even know.
        final File cfg = new File(this.getDataFolder() + "/config.yml");
        final File cfg1 = new File(this.getDataFolder() + "/cloud.yml");
        final File cfg2 = new File(this.getDataFolder() + "/filter.yml");
        final File cfg3 = new File(this.getDataFolder() + "/brand.yml");

        final File dir1 = new File(this.getDataFolder() + "/logs");
        if (dir1.mkdir()) {
            this.getLogger().info("Created logs directory");
        }

        final File dir2 = new File(this.getDataFolder() + "/deprecated0");
        if (dir2.mkdir()) {
            this.getLogger().info("Created deprecated directory.");
            if (cfg.renameTo(new File(dir2 + "/config.yml")) && cfg1.renameTo(new File(dir2 + "/cloud.yml")) && cfg2.renameTo(new File(dir2 + "/filter.yml")) && cfg3.renameTo(new File(dir2 + "/brand.yml"))) {
                this.getLogger().info("Deprecated old configurations.");
            }
        }
        final File dir3 = new File(this.getDataFolder() + "/data");
        if (dir3.mkdir()) {
            this.getLogger().info("Created data directory");
        }
    }
}
