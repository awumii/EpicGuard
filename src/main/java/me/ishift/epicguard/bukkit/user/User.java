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
import me.ishift.epicguard.common.AttackManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final boolean exists;
    private final String name;
    private Json data;

    /**
     * @param name Nickname of the user.
     * @param attackManager Instance of the AttackManager (needed for GeoIP query).
     */
    public User(String name, AttackManager attackManager) {
        this.name = name;
        final File file = new File("plugins/EpicGuard/data/users/" + name + ".json");
        this.exists = file.exists();

        final Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            this.data = new Json(name, "plugins/EpicGuard/data/users");

            final String address = player.getAddress().getAddress().getHostAddress();
            this.data.set("uuid", player.getUniqueId().toString());
            this.data.set("address", address);
            this.data.set("country", attackManager.getGeoApi().getCountryCode(address));
            this.data.set("city", attackManager.getGeoApi().getCity(address));
        }
    }

    public boolean exists() {
        return this.exists;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return this.data.getString("uuid");
    }

    public boolean isOnline() {
        return Bukkit.getPlayerExact(this.name) != null;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(this.getUUID()));
    }

    public String getCountry() {
        return this.data.getString("country");
    }

    public String getCity() {
        return this.data.getString("city");
    }

    public List<String> getAddressHistory() {
        return this.data.getOrSetDefault("address-history", new ArrayList<>());
    }

    public void setAddressHistory(List<String> addressHistory) {
        this.data.set("address-history", addressHistory);
    }

    public String getAddress() {
        return this.data.getString("address");
    }

    public boolean isNotifications() {
        return this.data.getOrSetDefault("notifications", false);
    }

    public void setNotifications(boolean notifications) {
        this.data.set("notifications", notifications);
    }
}
