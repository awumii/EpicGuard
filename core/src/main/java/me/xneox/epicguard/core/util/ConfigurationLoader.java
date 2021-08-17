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

package me.xneox.epicguard.core.util;

import java.io.File;
import me.xneox.epicguard.core.proxy.ProxyService;
import me.xneox.epicguard.core.proxy.ProxyServiceSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;

public class ConfigurationLoader<C> {
  private final HoconConfigurationLoader loader;
  private ObjectMapper<C> mapper;

  public ConfigurationLoader(@NotNull File file, @NotNull Class<C> implementation) {
    this.loader = HoconConfigurationLoader.builder()
        .defaultOptions(opt -> opt.serializers(builder -> builder.register(ProxyService.class, ProxyServiceSerializer.INSTANCE)))
        .file(file)
        .build();

    try {
      this.mapper = ObjectMapper.factory().get(implementation);
    } catch (SerializationException e) {
      e.printStackTrace();
    }
  }

  @NotNull
  public C load() throws ConfigurateException {
    C configuration = this.mapper.load(this.loader.load());

    this.save(configuration); // write default values
    return configuration;
  }

  public void save(@NotNull C config) throws ConfigurateException {
    CommentedConfigurationNode node = this.loader.createNode();
    this.mapper.save(config, node);
    this.loader.save(node);
  }
}
