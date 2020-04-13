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

package me.ishift.epicguard.bukkit.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TabCompletePacketListener extends PacketAdapter {
    public TabCompletePacketListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE);
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        // Blocking TabComplete
        if (SpigotSettings.tabCompleteBlock) {
            event.setCancelled(true);
            return;
        }

        // Custom TabComplete.
        final Player player = event.getPlayer();
        if (SpigotSettings.customTabCompleteBypass && player.hasPermission("epicguard.bypass.custom-tab-complete")) {
            return;
        }

        final PacketContainer packetContainer = event.getPacket();
        final String message = packetContainer.getStrings().read(0);

        if (message.startsWith("/") && SpigotSettings.customTabComplete) {
            final String command = message.split(" ")[0].substring(1).toLowerCase();

            if (message.contains(" ") && !SpigotSettings.customTabCompleteList.contains(command)) {
                event.setCancelled(true);
                return;
            }

            final PacketContainer response = new PacketContainer(PacketType.Play.Server.TAB_COMPLETE);
            event.setCancelled(true);
            response.getStringArrays().write(0, SpigotSettings.customTabCompleteList.toArray(new String[0]));

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}