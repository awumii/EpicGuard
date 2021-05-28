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

package me.xneox.epicguard.core.config;

import me.xneox.epicguard.core.proxy.ProxyService;
import me.xneox.epicguard.core.proxy.ProxyServiceSerializer;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import javax.annotation.Nonnull;
import java.io.File;

public class ConfigLoader<C> {
    private final YamlConfigurationLoader loader;
    private ObjectMapper<C> mapper;

    public ConfigLoader(@Nonnull File file, @Nonnull Class<C> implementation) {
        this.loader = YamlConfigurationLoader.builder()
                .defaultOptions(opt -> opt.serializers(builder -> builder.register(ProxyService.class, ProxyServiceSerializer.INSTANCE)))
                .file(file)
                .build();

        try {
            this.mapper = ObjectMapper.factory().get(implementation);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    public C load() throws ConfigurateException {
        return this.mapper.load(this.loader.load());
    }

    public void save(@Nonnull C config) throws ConfigurateException {
        CommentedConfigurationNode node = this.loader.createNode();
        this.mapper.save(config, node);
        this.loader.save(node);
    }
}
