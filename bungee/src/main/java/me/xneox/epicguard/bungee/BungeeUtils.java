package me.xneox.epicguard.bungee;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;

public final class BungeeUtils {
    public static BaseComponent[] toLegacyComponent(Component component) {
        return BungeeComponentSerializer.get().serialize(component);
    }

    private BungeeUtils() {}
}
