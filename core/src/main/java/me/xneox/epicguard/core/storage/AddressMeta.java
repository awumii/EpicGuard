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

package me.xneox.epicguard.core.storage;

import java.util.List;

public class AddressMeta {
    private final List<String> nicknames;
    private boolean blacklisted;
    private boolean whitelisted;

    public AddressMeta(boolean blacklisted, boolean whitelisted, List<String> nicknames) {
        this.blacklisted = blacklisted;
        this.whitelisted = whitelisted;
        this.nicknames = nicknames;
    }

    public boolean blacklisted() {
        return this.blacklisted;
    }

    public void blacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public boolean whitelisted() {
        return this.whitelisted;
    }

    public void whitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public List<String> nicknames() {
        return this.nicknames;
    }
}
