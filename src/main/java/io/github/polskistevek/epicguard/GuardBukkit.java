package io.github.polskistevek.epicguard;

import io.github.polskistevek.epicguard.command.GuardCommand;
import io.github.polskistevek.epicguard.gui.GuiMain;
import io.github.polskistevek.epicguard.gui.GuiPlayers;
import io.github.polskistevek.epicguard.bukkit.listener.*;
import io.github.polskistevek.epicguard.listener.*;
import io.github.polskistevek.epicguard.manager.DataFileManager;
import io.github.polskistevek.epicguard.manager.FileManager;
import io.github.polskistevek.epicguard.manager.UserManager;
import io.github.polskistevek.epicguard.object.CustomFile;
import io.github.polskistevek.epicguard.task.ActionBarTask;
import io.github.polskistevek.epicguard.task.AttackTask;
import io.github.polskistevek.epicguard.task.SaveTask;
import io.github.polskistevek.epicguard.util.ExactTPS;
import io.github.polskistevek.epicguard.util.MessagesBukkit;
import io.github.polskistevek.epicguard.util.Metrics;
import io.github.polskistevek.epicguard.util.MiscUtil;
import io.github.polskistevek.epicguard.util.nms.NMSUtil;
import io.github.polskistevek.epicguard.universal.ConfigProvider;
import io.github.polskistevek.epicguard.util.GeoDataase;
import io.github.polskistevek.epicguard.util.Logger;
import io.github.polskistevek.epicguard.util.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuardBukkit extends JavaPlugin {
    public static final String PERMISSION = "epicguard.admin";

    public static File dataFolder;

    @Override
    public void onEnable() {
        try {
            final long ms = System.currentTimeMillis();
            dataFolder = this.getDataFolder();
            this.saveDefaultConfig();
            this.createDirectories();
            new Logger(ServerType.SPIGOT);
            this.drawLogo();
            new ConfigProvider(new File(this.getDataFolder() + "/config.yml"));
            new GeoDataase(ServerType.SPIGOT);
            new Metrics(this);
            new NMSUtil();
            DataFileManager.load();
            DataFileManager.save();
            MessagesBukkit.load();
            Logger.info("NMS Version: " + NMSUtil.getVersion());
            this.registerTasks();
            this.registerListeners();
            GuiMain.eq = Bukkit.createInventory(null, 45, "EpicGuard Management Menu");
            GuiPlayers.inv = Bukkit.createInventory(null, 36, "EpicGuard Player Manager");

            this.getCommand("epicguard").setExecutor(new GuardCommand());
            this.registerBrand();
            this.fixVariables();
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
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        if (pm.isPluginEnabled("ProtocolLib")){
            MiscUtil.registerProtocolLib(this);
        }
    }

    private void registerTasks() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarTask(), 0L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 0L, ConfigProvider.ATTACK_TIMER);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(), 0L, 5000L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ExactTPS(), 100L, 1L);
    }

    private void createDirectories() {
        File cfg = new File(this.getDataFolder() + "/config.yml");
        File dir1 = new File(this.getDataFolder() + "/logs");
        if (!dir1.exists()){
            dir1.mkdir();
        }
        File dir2 = new File(this.getDataFolder() + "/deprecated");
        if (!dir2.exists()){
            cfg.renameTo(new File(dir2 + "/config.yml"));
            dir2.mkdir();
        }
        File dir3 = new File(this.getDataFolder() + "/data");
        if (!dir3.exists()){
            dir3.mkdir();
        }
    }

    private void fixVariables() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UserManager.addUser(player);
        }
    }

    private void registerBrand() {
        FileManager.createFile(this.getDataFolder() + "/brand.yml");
        CustomFile brandConfig = FileManager.getFile(this.getDataFolder() + "/brand.yml");
        if (!brandConfig.isExisting()){
            List<String> blockedBrandDefault = new ArrayList<>();
            blockedBrandDefault.add("some_blocked_brand");
            brandConfig.getConfig().set("channel-verification.enabled", true);
            brandConfig.getConfig().set("channel-verification.punish", "kick {PLAYER} &cException occurred in your connection, please rejoin!");
            brandConfig.getConfig().set("blocked-brands.enabled", true);
            brandConfig.getConfig().set("blocked-brands.punish", "kick {PLAYER} &cYour client is not allowed on this server!");
            brandConfig.getConfig().set("blocked-brands.list", blockedBrandDefault);
            brandConfig.save();
        }

        Messenger messenger = Bukkit.getMessenger();
        if (NMSUtil.isOldVersion()) {
            messenger.registerIncomingPluginChannel(this, "MC|Brand", new BrandPluginMessageListener());
        } else {
            messenger.registerIncomingPluginChannel(this, "minecraft:brand", new BrandPluginMessageListener());
        }
    }

    private void drawLogo() {
        try {
            final Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/YwUWQ8WC").openStream());
            while (scanner.hasNextLine()){
                Logger.info(scanner.nextLine());
            }
            scanner.close();
            Logger.info("Created by iShift.");
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
