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

package me.ishift.epicguard.common.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public abstract class DataStorage {
    private Collection<String> rejoinData;
    private Collection<String> pingData;
    private Collection<String> blacklist;
    private Collection<String> whitelist;

    /**
     * @param address Blacklisting the specified address.
     */
    public void blacklist(String address) {
        this.blacklist.add(address);
    }

    /**
     * @param address Whitelisting the specified address.
     * It also removes it from blacklist.
     */
    public void whitelist(String address) {
        this.blacklist.remove(address);
        this.whitelist.add(address);
    }

    /**
     * Method executed when storage is loading.
     */
    public abstract void load();

    /**
     * Method executed when storage is saving.
     */
    public abstract void save();
}
