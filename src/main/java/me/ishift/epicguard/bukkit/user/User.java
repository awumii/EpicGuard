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

package me.ishift.epicguard.bukkit.user;

import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import me.ishift.epicguard.common.AttackManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private final AttackManager manager;
    private final boolean existing;
    private final String name;
    private Json data;

    private String uuid;
    private String address;
    private String country;
    private String city;
    private List<String> addressHistory;
    private boolean notifications;

    /**
     * @param name Nickname of the user.
     * @param manager Instance of the AttackManager (needed for GeoIP query).
     */
    public User(String name, AttackManager manager) {
        this.manager = manager;
        this.name = name;
        final File file = new File("plugins/EpicGuard/data/users/" + name + ".json");
        this.existing = file.exists();
    }

    public void create() {
        this.data = new Json(this.name, "plugins/EpicGuard/data/users");
        final Player player = Bukkit.getPlayerExact(this.name);
        if (player != null) {
            final String address = player.getAddress().getAddress().getHostAddress();
            this.addressHistory = this.data.getOrSetDefault("address-history", new ArrayList<>());
            this.uuid = player.getUniqueId().toString();
            this.address = address;
            this.country = this.manager.getGeoApi().getCountryCode(address);
            this.city = this.manager.getGeoApi().getCity(address);
        }
    }

    public void save() {
        this.data.set("uuid", this.uuid);
        this.data.set("address", this.address);
        this.data.set("country", this.country);
        this.data.set("city", this.city);
        this.data.set("address-history", this.addressHistory);
        this.data.set("notifications", this.notifications);
    }

    public Player getPlayer() {
        return Bukkit.getPlayerExact(this.name);
    }
}
