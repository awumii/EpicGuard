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

package me.ishift.epicguard.api;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Logger;
import me.ishift.epicguard.common.Downloader;
import me.ishift.epicguard.common.types.GeoMode;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GeoAPI {
    private DatabaseReader countryReader;
    private DatabaseReader cityReader;

    public GeoAPI() {
        try {
            Logger.info("This product includes GeoLite2 data created by MaxMind, available from www.maxmind.com");
            Logger.info("By using this software, you agree to GeoLite2 EULA (https://www.maxmind.com/en/geolite2/eula)");

            final File countryFile = new File("plugins/EpicGuard/data/GeoLite2-Country.mmdb");
            final File cityFile = new File("plugins/EpicGuard/data/GeoLite2-City.mmdb");
            final File timestampFile = new File("plugins/EpicGuard/data/last_db_download.txt");

            if (Config.countryMode != GeoMode.DISABLED) {
                if (!countryFile.exists() || isOutdated(timestampFile)) {
                    Downloader.download("https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-Country.mmdb", countryFile);
                    Logger.eraseFile(timestampFile);
                    Logger.writeToFile(timestampFile, String.valueOf(System.currentTimeMillis()));
                }
                countryReader = new DatabaseReader.Builder(countryFile).withCache(new CHMCache()).build();
            }

            if (Config.geoCity) {
                if (!cityFile.exists() || isOutdated(timestampFile)) {
                    Downloader.download("https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-City.mmdb", countryFile);
                    Logger.eraseFile(timestampFile);
                    Logger.writeToFile(timestampFile, String.valueOf(System.currentTimeMillis()));
                }
                cityReader = new DatabaseReader.Builder(cityFile).withCache(new CHMCache()).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param host Host address of target
     * @return ISO-Code of target's country. Returns "Unknown?" if not found.
     */
    public String getCountryCode(String host) {
        final InetAddress address = getAddress(host);

        if (address != null && getCountryReader() != null) {
            if (!address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                try {
                    return getCountryReader().country(address).getCountry().getIsoCode();
                } catch (IOException | GeoIp2Exception e) {
                    Logger.info("[GeoIp2Exception/IOException] Can't find country for address: " + host);
                }
            }
        }
        return "Unknown?";
    }

    /**
     * @param host Host address of target
     * @return Name of target's city. Returns "Unknown?" if not found.
     */
    public String getCity(String host) {
        final InetAddress address = getAddress(host);

        if (address != null && getCityReader() != null) {
            if (!address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                try {
                    return countryReader.city(address).getCity().getName();
                } catch (IOException | GeoIp2Exception e) {
                    Logger.info("[GeoIp2Exception/IOException] Can't find city for address: " + host);
                }
            }
        }
        return "Unknown?";
    }

    /**
     *
     * @return DatabaseReader object for city database. Can be null if database is disabled.
     */
    public DatabaseReader getCityReader() {
        return cityReader;
    }

    /**
     * @return DatabaseReader for country database. Can be null if database is disabled.
     */
    public DatabaseReader getCountryReader() {
        return countryReader;
    }

    /**
     * @param hostname Host address.
     * @return InetAddress from host address.
     */
    public InetAddress getAddress(String hostname) {
        try {
            return InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            Logger.info("[UnknownHostException] Can't resolve InetAddress from hostname: " + hostname);
        }
        return null;
    }

    /**
     * @param dateFile File with last download timestamp
     * @return Boolean whether the database is outdated or not.
     * @throws IOException If file can't be created for some reason.
     */
    private boolean isOutdated(File dateFile) throws IOException {
        if (dateFile.createNewFile()) {
            return true;
        }
        final Scanner scanner = new Scanner(dateFile);
        final long timeOld = Long.parseLong(scanner.next());
        return (System.currentTimeMillis() - timeOld) > 604800000;
    }
}
