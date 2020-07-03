package me.ishift.epicguard.core.manager;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GeoManager {
    private final EpicGuard epicGuard;

    private DatabaseReader countryReader;
    private DatabaseReader cityReader;

    public GeoManager(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
        File countryDatabase = new File("plugins/EpicGuard/geo/GeoLite2-Country.mmdb");
        File cityDatabase = new File("plugins/EpicGuard/geo/GeoLite2-Country.mmdb");

        if (this.shouldDownload(countryDatabase)) {
            FileUtils.downloadFile("https://github.com/xishift/EpicGuard/raw/master/files/GeoLite2-Country.mmdb", countryDatabase);
        }

        if (this.shouldDownload(cityDatabase)) {
            FileUtils.downloadFile("https://github.com/xishift/EpicGuard/raw/master/files/GeoLite2-City.mmdb", cityDatabase);
        }

        try {
            this.countryReader = new DatabaseReader
                    .Builder(countryDatabase)
                    .withCache(new CHMCache())
                    .build();

            this.cityReader = new DatabaseReader
                    .Builder(countryDatabase)
                    .withCache(new CHMCache())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCountryCode(String host) {
        final InetAddress address = this.getAddress(host);

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
        final InetAddress address = this.getAddress(host);

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

    private boolean shouldDownload(File file) {
        long lastDownload = this.epicGuard.getStorageManager().getData().getLong("last-db-download");
        return !file.exists() || (System.currentTimeMillis() - lastDownload) > 604800000;
    }
}
