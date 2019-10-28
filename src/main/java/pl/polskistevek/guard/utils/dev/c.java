package pl.polskistevek.guard.utils.dev;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.ChatUtil;

public class c {
    public static void a() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BukkitMain.getPlugin(BukkitMain.class),
                ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {
            public void onPacketReceiving(PacketEvent event) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatUtil.fix(BukkitMain.PREFIX + event.getPacket().getProtocols().toString()));
            }
        });
    }
}
