package me.ishift.epicguard.bukkit.module;

import de.leonhard.storage.Yaml;
import me.ishift.epicguard.bukkit.module.modules.*;
import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules;
    private final EpicGuard epicGuard;

    public boolean opProtectionEnable;
    public String opProtectionCommand;
    public List<String> opProtectionList;

    public boolean blockedCommandsEnable;
    public boolean blockedCommandsBypass;
    public List<String> blockedCommands;

    public boolean allowedCommandsEnable;
    public boolean allowedCommandsBypass;
    public List<String> allowedCommands;

    public boolean tabCompleteBlock;
    public boolean customTabComplete;
    public boolean customTabCompleteBypass;
    public List<String> customTabCompleteList;

    public boolean disableOperatorMechanics;
    public boolean disableOperatorMechanicsConsole;
    public boolean deopOnEnable;

    public boolean blockNamespacedCommands;
    public boolean blockNamespacedCommandsWhitelistEnabled;
    public List<String> blockNamespacedCommandsWhitelist;
    public boolean blockNamespacedCommandsBypass;

    public boolean superAdminEnabled;
    public List<String> superAdminList;

    public ModuleManager(EpicGuard epicGuard) {
        Yaml config = new Yaml("spigot.yml", "plugins/EpicGuard");
        config.setHeader("If you don't know what these settings do, feel free to ask in the discord server.");

        //tabCompleteBlock = config.getOrSetDefault("fully-block-tab-complete", false);

        allowedCommands = config.getOrSetDefault("allowed-commands.list", Arrays.asList("/example", "/example2"));
        allowedCommandsBypass = config.getOrSetDefault("allowed-commands.bypass-permission", true);
        allowedCommandsEnable = config.getOrSetDefault("allowed-commands.enabled", false);

        opProtectionEnable = config.getOrSetDefault("op-protection.enabled", false);
        opProtectionList = config.getOrSetDefault("op-protection.list", Arrays.asList("YourName", "YourAdmin2"));
        opProtectionCommand = config.getOrSetDefault("op-protection.punish", "ban {PLAYER} You are not allowed to have OP!");

        blockedCommandsEnable = config.getOrSetDefault("blocked-commands.enabled", false);
        blockedCommandsBypass = config.getOrSetDefault("blocked-commands.bypass-permission", false);
        blockedCommands = config.getOrSetDefault("blocked-commands.list", Arrays.asList("/example", "/example2"));

        //customTabComplete = config.getOrSetDefault("custom-tab-complete.enabled", false);
        //customTabCompleteList = config.getOrSetDefault("custom-tab-complete.list", Arrays.asList("/example", "/example2"));
        //customTabCompleteBypass = config.getOrSetDefault("custom-tab-complete.bypass-permission", true);

        disableOperatorMechanics = config.getOrSetDefault("disable-vanilla-operator-mechanics", false);
        disableOperatorMechanicsConsole = config.getOrSetDefault("disable-vanilla-operator-mechanics-in-console", false);
        deopOnEnable = config.getOrSetDefault("deop-all-operators-on-startup", false);

        blockNamespacedCommands = config.getOrSetDefault("block-namespaced-commands.enabled", false);
        blockNamespacedCommandsWhitelistEnabled = config.getOrSetDefault("block-namespaced-commands.whitelist.enabled", false);
        blockNamespacedCommandsWhitelist = config.getOrSetDefault("block-namespaced-commands.whitelist.commands", Arrays.asList("/plugin:command1", "/plugin:command2"));
        blockNamespacedCommandsBypass = config.getOrSetDefault("block-namespaced-commands.bypass-permission", false);

        superAdminEnabled = config.getOrSetDefault("super-admin.enabled", false);
        superAdminList = config.getOrSetDefault("super-admin.list", Collections.singletonList("Owner"));

        this.epicGuard = epicGuard;
        this.modules = new ArrayList<>();
        this.modules.add(new OperatorProtection(this));
        this.modules.add(new BlockedCommands(this));
        this.modules.add(new AllowedCommands(this));
        this.modules.add(new NamespacedCommands(this));
        this.modules.add(new OperatorMechanics(this));

        if (this.deopOnEnable) {
            Bukkit.getOperators().forEach(operator -> operator.setOp(false));
        }
    }

    public boolean check(Player player, String command, String[] args) {
        if (this.superAdminEnabled && this.superAdminList.contains(player.getName())) {
            return false;
        }

        for (Module module : this.getModules()) {
            if (module.execute(player, command, args)) {
                return true;
            }
        }
        return false;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public EpicGuard getEpicGuard() {
        return this.epicGuard;
    }
}
