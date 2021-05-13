package me.xneox.epicguard.velocity;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class AdventureUtils {
    public static TextComponent createComponent(String text) {
        return LegacyComponentSerializer.legacy('&').deserialize(text);
    }

    private AdventureUtils() {}
}
