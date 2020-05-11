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

import me.ishift.epicguard.common.antibot.Detection;
import me.ishift.epicguard.common.antibot.ProxyService;
import me.ishift.epicguard.common.antibot.checks.*;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.LibraryLoader;

import java.util.Collection;
import java.util.HashSet;

public class AttackManager {
    private final Collection<ProxyService> proxyServices;
    private final GeoApi geoApi;
    private final GuardLogger logger;

    private final BlacklistCheck blacklistCheck;
    private final GeographicalCheck geographicalCheck;
    private final NicknameCheck nicknameCheck;
    private final ProxyCheck proxyCheck;
    private final ReJoinCheck reJoinCheck;
    private final ServerListCheck serverListCheck;

    private int connectPerSecond = 0;
    private int totalBots = 0;
    private boolean attackMode = false;

    /**
     * Creating new AttackManager object.
     * AttackManager holds variables/objects which are used
     * on the all platforms (Bukkit/Bungee/Velocity).
     */
    public AttackManager() {
        this.proxyServices = new HashSet<>();
        this.logger = new GuardLogger("EpicGuard", "plugins/EpicGuard", this);
        this.logger.info("Loading libraries...");
        LibraryLoader.init();
        this.logger.info("Loading configuration...");
        Configuration.load();
        Messages.load();
        this.logger.info("Loading storage...");
        StorageManager.load();

        this.logger.info("Loading antibot checks...");
        this.blacklistCheck = new BlacklistCheck();
        this.geographicalCheck = new GeographicalCheck(this);
        this.nicknameCheck = new NicknameCheck();
        this.proxyCheck = new ProxyCheck(this);
        this.reJoinCheck = new ReJoinCheck(this);
        this.serverListCheck = new ServerListCheck(this);

        if (Configuration.advancedProxyChecker) {
            this.proxyServices.addAll(Configuration.proxyServices);
        }
        this.logger.info("Initializing GeoApi...");
        this.geoApi = new GeoApi("plugins/EpicGuard", Configuration.countryEnabled, Configuration.cityEnabled, this);
        this.logger.info("Startup completed!");
    }

    /**
     * @return Current instance of the GuardLogger.
     */
    public GuardLogger getLogger() {
        return this.logger;
    }

    /**
     * Performing the bot-check.
     *
     * @param address Address of the user.
     * @param nickname Nickname of the user.
     * @return Detection object with prepared checks.
     */
    public Detection check(String address, String nickname) {
        return new Detection(address, nickname, this);
    }

    /**
     * @return Current instance of the GeoApi.
     */
    public GeoApi getGeoApi() {
        return this.geoApi;
    }

    /**
     * Increase connections per second by one.
     */
    public void increaseConnectPerSecond() {
        this.connectPerSecond++;
    }

    /**
     * @param i Amount of connections per second.
     */
    public void setConnectPerSecond(int i) {
        this.connectPerSecond = i;
    }

    /**
     * Increasing number of bots blocked during current bot-attack by one.
     */
    public void increaseBots() {
        this.totalBots++;
    }

    /**
     * @return True if server is under attack, or false if not.
     */
    public boolean isUnderAttack() {
        return this.attackMode;
    }

    /**
     * @return Amount of connections per second.
     */
    public int getConnectPerSecond() {
        return this.connectPerSecond;
    }

    /**
     * @return Amount of bots blocked during current bot-attack session.
     */
    public int getTotalBots() {
        return this.totalBots;
    }

    /**
     * @return Collection of the ProxyService.
     */
    public Collection<ProxyService> getProxyServices() {
        return this.proxyServices;
    }

    /**
     * @param bol Toggling the AttackMode.
     */
    public void setAttackMode(boolean bol) {
        this.attackMode = bol;
    }

    /**
     * Resetting antibot variables to the default values.
     */
    public void reset() {
        this.attackMode = false;
        this.totalBots = 0;
        this.connectPerSecond = 0;
    }

    public BlacklistCheck getBlacklistCheck() {
        return blacklistCheck;
    }

    public GeographicalCheck getGeographicalCheck() {
        return geographicalCheck;
    }

    public NicknameCheck getNicknameCheck() {
        return nicknameCheck;
    }

    public ProxyCheck getProxyCheck() {
        return proxyCheck;
    }

    public ReJoinCheck getReJoinCheck() {
        return reJoinCheck;
    }

    public ServerListCheck getServerListCheck() {
        return serverListCheck;
    }
}
