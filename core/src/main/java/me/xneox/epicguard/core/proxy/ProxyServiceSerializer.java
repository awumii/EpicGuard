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

package me.xneox.epicguard.core.proxy;

import java.lang.reflect.Type;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

/**
 * Configurate serializer and deserializer for the configured {@link ProxyService}.
 */
public class ProxyServiceSerializer implements TypeSerializer<ProxyService> {
  public static final ProxyServiceSerializer INSTANCE = new ProxyServiceSerializer();

  @Override
  public ProxyService deserialize(Type type, ConfigurationNode node) throws SerializationException {
    var urlNode = node.node("url");
    String url = urlNode.getString();

    var matcherNode = node.node("matcher");
    String matcher = matcherNode.getString();

    if (url == null || matcher == null) {
      throw new SerializationException("Invalid proxy-services configuration.");
    }
    return new ProxyService(url, Pattern.compile(matcher));
  }

  @Override
  public void serialize(Type type, @Nullable ProxyService service, ConfigurationNode target) throws SerializationException {
    if (service == null) {
      target.raw(null);
      return;
    }

    target.node("url").set(service.url());
    target.node("matcher").set(service.matcher().pattern());
  }
}
