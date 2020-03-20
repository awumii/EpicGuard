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

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<String> addressHistory;
    private boolean notifications;
    private final String address;

    public User(Player player) {
        this.notifications = false;
        this.address = player.getAddress().getAddress().getHostAddress();
        this.addressHistory = new ArrayList<>();
    }

    public List<String> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<String> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public String getAddress() {
        return this.address;
    }

    public boolean isNotifications() {
        return this.notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
