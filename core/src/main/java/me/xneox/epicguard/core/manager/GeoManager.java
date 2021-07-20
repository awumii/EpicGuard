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

package me.xneox.epicguard.core.manager;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.util.FileUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * This class manages the GeoLite2's databases, downloads and updates them if needed.
 * It also contains methods for easy database access.
 */
public class GeoManager {
    private final GuardLogger logger;

    private DatabaseReader countryReader;
    private DatabaseReader cityReader;

    public GeoManager(GuardLogger logger) {
        this.logger = logger;
        logger.info("This product includes GeoLite2 data created by MaxMind, available from https://www.maxmind.com");

        String parent = "plugins/EpicGuard/data";
        File countryDatabase = new File(parent, "GeoLite2-Country.mmdb");
        File cityDatabase = new File(parent, "GeoLite2-City.mmdb");
        File countryArchive = new File(parent, "GeoLite2-Country.tar.gz");
        File cityArchive = new File(parent, "GeoLite2-City.tar.gz");

        try {
            this.downloadDatabase(countryDatabase, countryArchive, "https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-Country&license_key=LARAgQo3Fw7W9ZMS&suffix=tar.gz");
            this.downloadDatabase(cityDatabase, cityArchive, "https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-City&license_key=LARAgQo3Fw7W9ZMS&suffix=tar.gz");

            this.countryReader = new DatabaseReader
                    .Builder(countryDatabase)
                    .withCache(new CHMCache())
                    .build();

            this.cityReader = new DatabaseReader
                    .Builder(cityDatabase)
                    .withCache(new CHMCache())
                    .build();
        } catch (IOException ex) {
            logger.warning("Couldn't download or initialize the GeoIP databases, please check your internet connection. Geographic features will be disabled.");
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void downloadDatabase(@NotNull File database, @NotNull File archive, @NotNull String url) throws IOException {
        Validate.notNull(database, "Database file cannot be null!");
        Validate.notNull(archive, "Archive file cannot be null!");
        Validate.notNull(url, "Download URL cannot be null!");

        if (!database.exists() || System.currentTimeMillis() - database.lastModified() > TimeUnit.DAYS.toMillis(7L)) {
            // Database does not exist or is outdated, and need to be downloaded.
            logger.info("Downloading the GeoIP database file: " + database.getName());
            FileUtils.downloadFile(url, archive);

            logger.info("Extracting the database from the tar archive...");
            TarArchiveInputStream tarInput = new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(archive)));
            TarArchiveEntry entry = tarInput.getNextTarEntry();
            while (entry != null) {
                // Extracting the database (.mmdb) database we are looking for.
                if (entry.getName().endsWith(database.getName())) {
                    IOUtils.copy(tarInput, new FileOutputStream(database));
                }
                entry = tarInput.getNextTarEntry();
            }

            // Closing InputStream and removing archive file.
            tarInput.close();
            archive.delete();
            logger.info("Database (" + database.getName() + ") has been extracted succesfuly.");
        }
    }

    @NotNull
    public String countryCode(@NotNull String address) {
        InetAddress inetAddress = this.parseAddress(address);
        if (inetAddress != null && this.countryReader != null) {
            try {
                return this.countryReader.country(inetAddress).getCountry().getIsoCode();
            } catch (IOException | GeoIp2Exception ex) {
                logger.warning("Couldn't find the country for the address " + address + ": " + ex.getMessage());
            }
        }
        return "unknown";
    }

    @NotNull
    public String city(@NotNull String address) {
        Validate.notNull(address, "Address cannot be null!");
        InetAddress inetAddress = this.parseAddress(address);
        if (inetAddress != null && this.cityReader != null) {
            try {
                return this.cityReader.city(inetAddress).getCity().getName();
            } catch (IOException | GeoIp2Exception ex) {
                logger.warning("Couldn't find the city for the address " + address + ": " + ex.getMessage());
            }
        }
        return "unknown";
    }

    @Nullable
    public InetAddress parseAddress(@NotNull String address) {
        Validate.notNull(address, "Address cannot be null!");
        try {
            return InetAddress.getByName(address);
        } catch (UnknownHostException ex) {
            logger.warning("Couldn't resolve the InetAddress for the host " + address + ": " + ex.getMessage());
        }
        return null;
    }
}
