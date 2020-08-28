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

package me.ishift.epicguard.core.manager;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.FileUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class GeoManager {
    private final EpicGuard epicGuard;

    private DatabaseReader countryReader;
    private DatabaseReader cityReader;

    public GeoManager(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
        this.epicGuard.getLogger().info("This product includes GeoLite2 data created by MaxMind, available from https://www.maxmind.com");

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadDatabase(File database, File archive, String url) throws IOException {
        if (!database.exists() || System.currentTimeMillis() - database.lastModified() > TimeUnit.DAYS.toMillis(7L)) {
            // Database does not exist or is outdated, and need to be downloaded.
            this.epicGuard.getLogger().info("Downloading the GeoIP database file: " + database.getName());
            FileUtils.downloadFile(url, archive);

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
        }
    }

    public String getCountryCode(String host) {
        InetAddress address = this.getAddress(host);
        if (address != null && this.countryReader != null) {
            try {
                return this.countryReader.country(address).getCountry().getIsoCode();
            } catch (IOException | GeoIp2Exception e) {
                e.printStackTrace();
            }
        }
        return "unknown";
    }

    public String getCity(String host) {
        InetAddress address = this.getAddress(host);
        if (address != null && this.cityReader != null) {
            try {
                return this.cityReader.city(address).getCity().getName();
            } catch (IOException | GeoIp2Exception e) {
                e.printStackTrace();
            }
        }
        return "unknown";
    }

    public InetAddress getAddress(String hostname) {
        try {
            return InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
