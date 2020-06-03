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

package me.ishift.epicguard.bukkit.inventory;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.util.Chat;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.ServerTPS;
import me.ishift.epicguard.bukkit.util.UMaterial;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.MemoryHelper;
import me.ishift.inventory.api.InventorySize;
import me.ishift.inventory.api.inventories.ClickableInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainInventory extends ClickableInventory {
    private final AttackManager manager;

    public MainInventory(AttackManager manager) {
        super("EpicGuard v" + EpicGuardBukkit.getInstance().getDescription().getVersion() + " (Main GUI)", "MAIN", InventorySize.BIG);
        this.manager = manager;
    }

    @Override
    public boolean isRefreshable() {
        return true;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        final ItemStack status = new ItemBuilder(UMaterial.CHEST_MINECART.getMaterial())
                .setTitle("&6Server status.")
                .addLore("")
                .addLore(" &8● &7Attack mode&8: " + (manager.isAttackMode() ? "&cActive!" : "&aNot active."))
                .addLore(" &8● &7Currently blocked&8: &6" + manager.getTotalBots() + " bots")
                .addLore("")
                .addLore(" &8● &7Connections&8: &2" + manager.getConnectPerSecond() + "/s")
                .addLore(" &8● &7Detections&8: &2" + manager.getDetectionsPerSecond() + "/s")
                .addLore("")
                .addLore(" &8» &cClick to toggle attack-mode.")
                .build();

        final ItemStack storage = new ItemBuilder(Material.CHEST)
                .setTitle("&6Storage management.")
                .addLore("")
                .addLore(" &8● &7Storage mode: &6" + (this.manager.getStorageManager().isMysql() ? "MySQL" : "Flat (JSON)"))
                .addLore(" &8● &7Last save&8: &6" + this.manager.getStorageManager().getLastSave())
                .addLore("")
                .addLore(" &8● &7Blacklisted IPs&8: &c" + this.manager.getStorageManager().getStorage().getBlacklist().size())
                .addLore(" &8● &7Whitelisted IPs&8: &a" + this.manager.getStorageManager().getStorage().getWhitelist().size())
                .addLore("")
                .addLore(" &8● &7Reconnect users&8: &2" + this.manager.getStorageManager().getStorage().getRejoinData().size())
                .addLore(" &8● &7PingData IPs&8: &2" + this.manager.getStorageManager().getStorage().getPingData().size())
                .addLore("")
                .addLore(" &8» &cClick to save the data.")
                .build();

        final ItemStack players = new ItemBuilder(Material.ARMOR_STAND)
                .setTitle("&6Player management menu.")
                .addLore("")
                .addLore(" &8● &7Online players: &6" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers())
                .addLore("")
                .addLore(" &8● &7This will show GUI with all players.")
                .addLore(" &8● &7You can see their &6address, country")
                .addLore(" &8● &6city, UUID and operator status&7!")
                .addLore("")
                .addLore(" &8» &cClick to open the GUI.")
                .build();

        final ItemStack server = new ItemBuilder(UMaterial.EXP_BOTTLE.getMaterial())
                .setTitle("&6Server Information.")
                .addLore("")
                .addLore(" &8● &7Version&8: &c" + Bukkit.getVersion())
                .addLore(" &8● &7Plugins&8: &c" + Bukkit.getPluginManager().getPlugins().length)
                .addLore(" &8● &7Online players: &6" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers())
                .addLore("")
                .addLore(" &8● &7Current TPS&8: &a" + ServerTPS.getTPS())
                .addLore(" &8● &7Memory usage&8: &a" + MemoryHelper.getUsage() + "MB / " + MemoryHelper.getTotal() + "MB")
                .build();

        final ItemStack cloud = new ItemBuilder(Material.BEACON)
                .setTitle("&6GuardCloud")
                .addLore("")
                .addLore(" &8● &bGuardCloud &7synchronizes the unsafe")
                .addLore(" &8● &7addresses with your local blacklist,")
                .addLore(" &8● &7detecting bots faster. Synchronization")
                .addLore(" &8● &7is performed every &f1 hour&7.")
                .addLore("")
                .addLore(" &8● &7Status: " + (this.manager.getCloud().isWorking() ? "&aWorking!" : "&cConnection error!"))
                .addLore(" &8● &7Unsafe addresses: &6" + this.manager.getCloud().getBlacklist().size())
                .addLore("")
                .addLore(" &8» &cClick to force the sync.")
                .build();

        final ItemStack info = new ItemBuilder(Material.PAPER)
                .setTitle("&6GUI info.")
                .addLore("")
                .addLore(" &8● &7This GUI is refreshed")
                .addLore(" &8● &7every &f2 seconds&7!")
                .build();

        final ItemStack about = new ItemBuilder(UMaterial.BOOK_AND_QUILL.getMaterial())
                .setTitle("&6About EpicGuard.")
                .addLore("")
                .addLore(" &8● &7Version&8: &e" + EpicGuardBukkit.getInstance().getDescription().getVersion())
                .addLore(" &8● &7Author&8: &eiShift")
                .addLore(" &8● &7Support Server&8: &cdiscord.gg/VkfhFCv")
                .addLore("")
                .addLore(" &8» &cClick to join our discord.")
                .build();

        inventory.setItem(11, status);
        inventory.setItem(13, storage);
        inventory.setItem(15, players);
        inventory.setItem(21, server);
        inventory.setItem(23, cloud);
        inventory.setItem(35, about);
        inventory.setItem(27, info);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getSlot() == 11) {
            this.manager.setAttackMode(!this.manager.isAttackMode());
            Chat.send(player, this.manager.isAttackMode() ? Messages.enabledAttackMode : Messages.disabledAttackMode);
        }

        if (event.getSlot() == 15) {
            player.closeInventory();
            EpicGuardBukkit.getInstance().getInventoryManager().open("PLAYERS", player);
        }

        if (event.getSlot() == 13) {
            Chat.send(player, Messages.savingData);
            this.manager.getStorageManager().save();
            Chat.send(player, Messages.savedData);
        }

        if (event.getSlot() == 23) {
            player.closeInventory();
            Chat.send(player, "discord.gg/VkfhFCv");
        }
    }
}
