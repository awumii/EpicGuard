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

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.GuardLogger;
import me.ishift.epicguard.common.util.Downloader;
import me.ishift.epicguard.common.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GeoApi {
    private DatabaseReader countryReader;
    private DatabaseReader cityReader;
    private final GuardLogger logger;

    /**
     * Creating new GeoAPI instance.
     *
     * @param basePath Base path where every files will be downloaded.
     * @param country  Should country database be enabled/downloaded?
     * @param city     Should city database be enabled/downloaded?
     */
    public GeoApi(String basePath, boolean country, boolean city, AttackManager manager) {
        this.logger = manager.getLogger();
        this.logger.info("This product includes GeoLite2 data created by MaxMind, available from www.maxmind.com");
        this.logger.info("By using this software, you agree to GeoLite2 EULA (https://www.maxmind.com/en/geolite2/eula)");

        final File countryFile = new File(basePath + "/data/GeoLite2-Country.mmdb");
        final File cityFile = new File(basePath + "/data/GeoLite2-City.mmdb");
        final File databaseAgeFile = new File(basePath + "/data/database.info");

        try {
            if (country) {
                if (!countryFile.exists() || isOutdated(databaseAgeFile)) {
                    this.logger.info("Downloading the GeoLite2-Country.mmdb file...");
                    Downloader.download("https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-Country.mmdb", countryFile);

                    FileUtil.eraseFile(databaseAgeFile);
                    FileUtil.writeToFile(databaseAgeFile, String.valueOf(System.currentTimeMillis()));
                }
                this.countryReader = new DatabaseReader.Builder(countryFile).withCache(new CHMCache()).build();
            }

            if (city) {
                if (!cityFile.exists() || isOutdated(databaseAgeFile)) {
                    this.logger.info("Downloading the GeoLite2-City.mmdb file...");
                    Downloader.download("https://github.com/PolskiStevek/EpicGuard/raw/master/files/GeoLite2-City.mmdb", cityFile);

                    FileUtil.eraseFile(databaseAgeFile);
                    FileUtil.writeToFile(databaseAgeFile, String.valueOf(System.currentTimeMillis()));
                }
                this.cityReader = new DatabaseReader.Builder(cityFile).withCache(new CHMCache()).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checking if the database is older than a week.
     *
     * @param dateFile File with last download timestamp
     * @return Boolean whether the database is outdated or not.
     * @throws IOException If file can't be created.
     */
    private boolean isOutdated(File dateFile) throws IOException {
        if (dateFile.createNewFile()) {
            return true;
        }
        final Scanner scanner = new Scanner(dateFile);
        final long timeOld = Long.parseLong(scanner.next());
        return (System.currentTimeMillis() - timeOld) > 604800000;
    }

    /**
     * @param host Host address of target
     * @return ISO-Code of target's country. or "Unknown?" if not found/database disabled.
     */
    public String getCountryCode(String host) {
        final InetAddress address = this.getAddress(host);

        if (address != null && this.getCountryReader() != null && !address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
            try {
                return this.getCountryReader().country(address).getCountry().getIsoCode();
            } catch (IOException | GeoIp2Exception e) {
                this.logger.info("Can't find country for address: " + host);
            }
        }
        return "Unknown?";
    }

    /**
     * @param host Host address of target
     * @return Name of target's city, or "Unknown?" if not found/database disabled.
     */
    public String getCity(String host) {
        final InetAddress address = this.getAddress(host);

        if (address != null && this.getCityReader() != null && !address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
            try {
                return this.getCityReader().city(address).getCity().getName();
            } catch (IOException | GeoIp2Exception e) {
                this.logger.info("Can't find city for address: " + host);
            }
        }
        return "Unknown?";
    }

    /**
     * @return DatabaseReader object for city database.
     * Can be null if database is disabled.
     */
    public DatabaseReader getCityReader() {
        return this.cityReader;
    }

    /**
     * @return DatabaseReader for country database.
     * Can be null if database is disabled.
     */
    public DatabaseReader getCountryReader() {
        return this.countryReader;
    }

    /**
     * @param hostname Host address.
     * @return InetAddress from host address.
     */
    public InetAddress getAddress(String hostname) {
        try {
            return InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            this.logger.info("Can't resolve InetAddress from hostname: " + hostname);
        }
        return null;
    }
}
