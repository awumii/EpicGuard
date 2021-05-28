package me.xneox.epicguard.core.proxy;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

/**
 * Configuration serializer and deserializer for ProxyService object.
 */
public class ProxyServiceSerializer implements TypeSerializer<ProxyService> {
    public static final ProxyServiceSerializer INSTANCE = new ProxyServiceSerializer();

    @Override
    public ProxyService deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ConfigurationNode urlNode = node.node("url");
        String url = urlNode.getString();

        ConfigurationNode responseRegexNode = node.node("responseRegex");
        String responseRegex = responseRegexNode.getString();

        if (url == null && responseRegex == null) {
            throw new SerializationException("Invalid proxy-services configuration.");
        }
        return new ProxyService(url, responseRegex);
    }

    @Override
    public void serialize(Type type, @Nullable ProxyService service, ConfigurationNode target) throws SerializationException {
        if (service == null) {
            target.raw(null);
            return;
        }

        target.node("url").set(service.url());
        target.node("responseRegex").set(service.responseContains());
    }
}
