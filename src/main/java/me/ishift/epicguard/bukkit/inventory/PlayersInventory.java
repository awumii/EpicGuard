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
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.util.ItemBuilder;
import me.ishift.epicguard.bukkit.util.SkullUtil;
import me.ishift.epicguard.bukkit.util.UMaterial;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.util.MessageHelper;
import me.ishift.inventory.api.InventorySize;
import me.ishift.inventory.api.inventories.ClickableInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayersInventory extends ClickableInventory {
    private final AttackManager manager;

    public PlayersInventory(AttackManager manager) {
        super("EpicGuard v" + EpicGuardBukkit.getInstance().getDescription().getVersion() + " (Players)", "PLAYERS", InventorySize.BIGGEST);
        this.manager = manager;
    }

    @Override
    public boolean isRefreshable() {
        return true;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        int i = 0;
        for (Player entry : Bukkit.getOnlinePlayers()) {
            final List<String> lore = new ArrayList<>();
            final User user = EpicGuardBukkit.getInstance().getUserManager().getUser(entry);

            lore.add("");
            lore.add(MessageHelper.color("&8» &cBasic Information"));
            lore.add(MessageHelper.color(" &8● &7Name&8: &f" + entry.getName()));
            lore.add(MessageHelper.color(" &8● &7UUID&8: &f" + entry.getUniqueId()));
            lore.add(MessageHelper.color(" &8● &7OP&8: " + (entry.isOp() ? "&2✔" : "&4✖")));
            lore.add(MessageHelper.color(" &8● &7Country&8: &f" + manager.getGeoApi().getCountryCode(user.getAddress())));
            lore.add(MessageHelper.color(" &8● &7City&8: &f" + manager.getGeoApi().getCity(user.getAddress())));

            if (!user.getAddressHistory().isEmpty()) {
                lore.add("");
                lore.add(MessageHelper.color(" &8» &cAddress History:"));
                user.getAddressHistory().forEach(address -> lore.add(MessageHelper.color("  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&6Current&8)" : ""))));
            }

            final ItemStack itemStack = SkullUtil.getSkull(entry, (entry.isOp() ? "&c[OP] " : "&a") + entry.getName(), lore);
            inventory.setItem(i, itemStack);
            i++;
        }

        final ItemStack back = new ItemBuilder(Material.ARROW).setTitle("&cMain menu").addLore("&8» &7Click to go back.").build();
        inventory.setItem(53, back);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getSlot() == 53) {
            player.closeInventory();
            EpicGuardBukkit.getInstance().getInventoryManager().open("MAIN", player);
        }
    }
}
