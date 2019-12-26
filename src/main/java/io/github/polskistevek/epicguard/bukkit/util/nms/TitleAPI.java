/*
  Author: ConnorLinfoot
  Plugin link: https://www.spigotmc.org/resources/titleapi-1-8-1-14-2.1325/
  I used parts of his code, so users don't need to download any other depencies
 */

package io.github.polskistevek.epicguard.bukkit.util.nms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class TitleAPI {

    @SuppressWarnings("ConstantConditions")
    public static void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, String title, String subtitle) {
        try {
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);
                title = title.replaceAll("%player%", player.getDisplayName());
                Object e = NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatTitle = NMSUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = NMSUtil.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtil.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object titlePacket = subtitleConstructor.newInstance(e, chatTitle, fadeIn, stay, fadeOut);
                NMSUtil.sendPacket(player, titlePacket);
                e = NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = NMSUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = NMSUtil.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtil.getNMSClass("IChatBaseComponent"));
                titlePacket = subtitleConstructor.newInstance(e, chatTitle);
                NMSUtil.sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                Object e = NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = NMSUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = NMSUtil.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtil.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                NMSUtil.sendPacket(player, subtitlePacket);
                e = NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = NMSUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = NMSUtil.getNMSClass("PacketPlayOutTitle").getConstructor(NMSUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSUtil.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                NMSUtil.sendPacket(player, subtitlePacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
