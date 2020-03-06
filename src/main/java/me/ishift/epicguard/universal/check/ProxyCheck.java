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

package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.URLHelper;

import java.io.IOException;
import java.util.List;

public class ProxyCheck {
    public static boolean perform(String address) {
        final String url = "http://proxycheck.io/v2/" + address + "?key=" + Config.apiKey;
        final List<String> response = URLHelper.readLines(url);

        if (response != null) {
            return response.contains("yes");
        }
        return false;
    }
}
