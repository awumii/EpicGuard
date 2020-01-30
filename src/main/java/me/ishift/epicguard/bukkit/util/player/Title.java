package me.ishift.epicguard.bukkit.util.player;

import me.ishift.epicguard.bukkit.util.server.Reflection;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class Title {
    public static void send(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            final Object chatTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            final Constructor<?> titleConstructor = Reflection.getNMSClass("PacketPlayOutTitle").getConstructor(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], Reflection.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object packet = titleConstructor.newInstance(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            final Object chatsTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            final Constructor<?> timingTitleConstructor = Reflection.getNMSClass("PacketPlayOutTitle").getConstructor(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], Reflection.getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            final Object timingPacket = timingTitleConstructor.newInstance(
                    Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            Reflection.sendPacket(player, packet);
            Reflection.sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
