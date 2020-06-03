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
import lombok.Setter;
import me.ishift.epicguard.common.antibot.ProxyService;
import me.ishift.epicguard.common.antibot.checks.*;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.types.Reason;

import java.util.Collection;
import java.util.LinkedList;

@Getter
public class AttackManager {
    private final Collection<ProxyService> proxyServices;
    private final StorageManager storageManager;
    private final GeoApi geoApi;
    private final GuardLogger logger;
    private final GuardCloud cloud;

    private final BlacklistCheck blacklistCheck;
    private final GeographicalCheck geographicalCheck;
    private final NicknameCheck nicknameCheck;
    private final ProxyCheck proxyCheck;
    private final ReJoinCheck reJoinCheck;
    private final ServerListCheck serverListCheck;

    @Setter private int connectPerSecond;
    @Setter private int detectionsPerSecond;
    @Setter private int totalBots;
    @Setter private boolean attackMode;

    /**
     * Creating new AttackManager object.
     * AttackManager holds variables/objects which are used
     * on the all platforms (Bukkit/Bungee/Velocity).
     */
    public AttackManager() {
        this.proxyServices = new LinkedList<>();
        this.logger = new GuardLogger("EpicGuard", "plugins/EpicGuard", this);
        this.logger.info("Loading libraries...");
        this.logger.info("Loading configuration...");
        Configuration.load();
        Messages.load();
        this.logger.info("Loading storage...");
        this.storageManager = new StorageManager();

        this.logger.info("Loading antibot checks...");
        this.blacklistCheck = new BlacklistCheck(this);
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

        this.logger.info("Loading GuardCloud...");
        this.cloud = new GuardCloud(this);

        this.logger.info("Startup completed!");
    }

    /**
     * Performing the bot-check.
     *
     * @param address Address of the user.
     * @param nickname Nickname of the user.
     * @return Reason enum if detected, null if not.
     */
    public Reason check(String address, String nickname) {
        final Reason reason = this.performChecks(address, nickname);
        if (reason == null) {
            return null;
        }

        this.increaseBots();
        if (reason.isBlacklist()) {
            this.storageManager.getStorage().blacklist(address);
        }
        return reason;
    }

    /**
     * Performing the bot-check.
     * This method performs the check for
     * the detections. The method above
     * need to be used in the listeners.
     *
     * @param address Address of the user.
     * @param nickname Nickname of the user.
     * @return Reason enum if detected, null if not.
     */
    private Reason performChecks(String address, String nickname) {
        this.increaseConnectPerSecond();
        if (this.storageManager.getStorage().getWhitelist().contains(address)) {
            return null;
        }

        /* After testing, looks like this check is useless.
           for some reason, block are blocked better without this.
           it may be restored in the future, so i have commented it for now.
        if (this.isAttackMode()) {
            return Reason.ATTACK;
        }
         */

        if (this.getBlacklistCheck().execute(address, nickname)) {
            return Reason.BLACKLIST;
        }

        if (this.cloud.getBlacklist().contains(address)) {
            return Reason.UNSAFE;
        }

        if (this.getNicknameCheck().execute(address, nickname)) {
            return Reason.NAME_CONTAINS;
        }

        if (this.getGeographicalCheck().execute(address, nickname)) {
            return Reason.GEO;
        }

        if (this.getServerListCheck().execute(address, nickname)) {
            return Reason.SERVER_LIST;
        }

        if (this.getReJoinCheck().execute(address, nickname)) {
            return Reason.REJOIN;
        }

        if (this.getProxyCheck().execute(address, nickname)) {
            return Reason.PROXY;
        }
        return null;
    }

    /**
     * Increase connections per second by one.
     */
    public void increaseConnectPerSecond() {
        if (this.getConnectPerSecond() > Configuration.connectSpeed || this.getDetectionsPerSecond() > Configuration.detections) {
            this.setAttackMode(true);
        }

        this.connectPerSecond++;
    }

    /**
     * Increasing number of bots blocked during current bot-attack by one.
     */
    public void increaseBots() {
        this.totalBots++;
        this.detectionsPerSecond++;
    }
}
