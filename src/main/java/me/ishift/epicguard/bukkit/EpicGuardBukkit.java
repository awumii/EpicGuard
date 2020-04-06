package me.ishift.epicguard.bukkit;

import fr.minuskube.inv.InventoryManager;
import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.PlayerCommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerInventoryClickListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.listener.ServerListPingListener;
import me.ishift.epicguard.bukkit.listener.TabCompletePacketListener;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.util.Log4jFilter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private static EpicGuardBukkit epicGuardBukkit;
    private UserManager userManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        epicGuardBukkit = this;
        this.saveDefaultConfig();
        Configuration.load();
        SpigotSettings.load();
        AttackManager.init();

        this.userManager = new UserManager();
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.init();

        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            new TabCompletePacketListener(this);
        }

        if (Configuration.filterEnabled) {
            final Log4jFilter filter = new Log4jFilter();
            filter.setFilteredMessages(Configuration.filterValues);
            filter.registerFilter();
        }

        final PluginCommand command = this.getCommand("guard");
        if (command != null) {
            command.setExecutor(new GuardCommand());
            command.setTabCompleter(new GuardTabCompleter());
        }
    }

    public static EpicGuardBukkit getInstance() {
        return epicGuardBukkit;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
