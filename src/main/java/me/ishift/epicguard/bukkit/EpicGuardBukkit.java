/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.inventory.MainInventory;
import me.ishift.epicguard.bukkit.inventory.PlayersInventory;
import me.ishift.epicguard.bukkit.listener.*;
import me.ishift.epicguard.bukkit.module.Module;
import me.ishift.epicguard.bukkit.module.modules.*;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterResetTask;
import me.ishift.epicguard.common.util.Log4jFilter;
import me.ishift.inventory.api.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.LinkedList;
import java.util.List;

public class EpicGuardBukkit extends JavaPlugin {
    private AttackManager attackManager;
    private InventoryManager inventoryManager;
    private OperatorProtection operatorProtection;
    private List<Module> modules;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.attackManager = new AttackManager();

        this.operatorProtection = new OperatorProtection();
        this.modules = new LinkedList<>();
        this.modules.add(this.operatorProtection);
        this.modules.add(new BlockedCommands());
        this.modules.add(new AllowedCommands());
        this.modules.add(new NamespacedCommands());
        this.modules.add(new OperatorMechanics());

        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.addInventory(new MainInventory(this.attackManager));
        this.inventoryManager.addInventory(new PlayersInventory(this.attackManager));

        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(this.attackManager), this);
        pm.registerEvents(new PlayerJoinListener(this.attackManager), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        pm.registerEvents(new ConsoleCommandListener(), this);
        pm.registerEvents(new PlayerBuildListener(), this);

        final BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new AttackToggleTask(this.attackManager), 0L, Configuration.checkConditionsDelay * 2L);
        scheduler.runTaskTimerAsynchronously(this, new CounterResetTask(this.attackManager), 0L, 20L);

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
            command.setExecutor(new GuardCommand(this.attackManager));
            command.setTabCompleter(new GuardTabCompleter());
        }
        new Metrics(this, 5845);

        SpigotSettings.load();
        if (SpigotSettings.deopOnEnable) {
            for (OfflinePlayer operator : Bukkit.getOperators()) {
                operator.setOp(false);
                this.getLogger().info("De-opped " + operator.getName() + ", because of the 'deop-on-enable' settings");
            }
        }
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public AttackManager getAttackManager() {
        return this.attackManager;
    }

    public static EpicGuardBukkit getInstance() {
        return JavaPlugin.getPlugin(EpicGuardBukkit.class);
    }

    public List<Module> getModules() {
        return modules;
    }

    public OperatorProtection getOperatorProtection() {
        return operatorProtection;
    }
}
