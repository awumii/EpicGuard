package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.PlayerCommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerInventoryClickListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.listener.TabCompletePacketListener;
import me.ishift.epicguard.bukkit.listener.ServerListPingListener;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.common.detection.AttackManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {
    private static EpicGuardBukkit epicGuardBukkit;
    private UserManager userManager;

    @Override
    public void onEnable() {
        epicGuardBukkit = this;
        AttackManager.init();
        this.userManager = new UserManager();

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

        final PluginCommand command = this.getCommand("guard");
        if (command != null) {
            command.setExecutor(new GuardCommand());
            command.setTabCompleter(new GuardTabCompleter());
            return;
        }
        throw new IllegalStateException("Could not create the command. This issue shouldn't ever happen, please contact author about this.");
    }

    public static EpicGuardBukkit getInstance() {
        return epicGuardBukkit;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
