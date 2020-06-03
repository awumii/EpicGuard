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

package me.ishift.epicguard.common;

import lombok.Getter;
import me.ishift.epicguard.common.util.URLHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
public class GuardCloud {
    private final AttackManager manager;
    private final Collection<String> blacklist;
    private boolean working;

    public GuardCloud(AttackManager manager) {
        this.manager = manager;
        this.blacklist = new HashSet<>();
        this.working = false;
    }

    /**
     * Synchronizes the local cloud blacklist with the remote cloud blacklist.
     */
    public void sync() {
        final List<String> response = URLHelper.readLines("https://raw.githubusercontent.com/xishift/EpicGuard/master/files/cloud_blacklist.txt");
        if (response == null) {
            this.manager.getLogger().info("Could not sync data with the cloud. Please check the information above.");
            this.working = false;
            return;
        }

        this.working = true;
        this.blacklist.clear();
        this.blacklist.addAll(response);
        this.manager.getLogger().info("Synchronized with the cloud (" + this.blacklist.size() + " total unsafe addresses blacklisted).");
    }
}
