package me.ishift.epicguard.bukkit.util.player;

import me.ishift.epicguard.bukkit.util.server.Reflection;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionBar {
    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + Reflection.getVersion() + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + Reflection.getVersion() + ".PacketPlayOutChat");
            Class<?> packetClass = Class.forName("net.minecraft.server." + Reflection.getVersion() + ".Packet");
            Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + Reflection.getVersion() + ".ChatComponentText");
            Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + Reflection.getVersion() + ".IChatBaseComponent");
            try {
                Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + Reflection.getVersion() + ".ChatMessageType");
                Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                Object chatMessageType = null;
                for (Object obj : chatMessageTypes) {
                    if (obj.toString().equals("GAME_INFO")) {
                        chatMessageType = obj;
                    }
                }
                Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(ChatUtil.fix(message));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
            } catch (ClassNotFoundException cnfe) {
                Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(ChatUtil.fix(message));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
