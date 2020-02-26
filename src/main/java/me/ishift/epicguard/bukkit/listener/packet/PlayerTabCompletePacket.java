package me.ishift.epicguard.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.ishift.epicguard.universal.Config;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerTabCompletePacket extends PacketAdapter {
    public PlayerTabCompletePacket(final Plugin plugin) {
        super(plugin, PacketType.Play.Client.TAB_COMPLETE);
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketReceiving(final PacketEvent event) {
        // Blocking TabComplete
        if (Config.tabCompleteBlock) {
            event.setCancelled(true);
            return;
        }

        // Custom TabComplete.
        final PacketContainer packetContainer = event.getPacket();
        final String message = packetContainer.getStrings().read(0);
        final Player player = event.getPlayer();

        if (Config.customTabCompleteBypass && player.hasPermission("epicguard.bypass.custom-tab-complete")) return;

        if (message.startsWith("/") && Config.customTabComplete) {
            final String command = message.split(" ")[0].substring(1).toLowerCase();

            if (message.contains(" ") && !Config.customTabCompleteList.contains(command)) {
                event.setCancelled(true);
                return;
            }

            final PacketContainer response = new PacketContainer(PacketType.Play.Server.TAB_COMPLETE);
            event.setCancelled(true);
            response.getStringArrays().write(0, Config.customTabCompleteList.toArray(new String[0]));

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}