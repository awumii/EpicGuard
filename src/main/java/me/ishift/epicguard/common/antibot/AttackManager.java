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

package me.ishift.epicguard.common.antibot;

import me.ishift.epicguard.common.antibot.checks.*;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.GeoApi;
import me.ishift.epicguard.common.util.LibraryLoader;

import java.util.Collection;
import java.util.HashSet;

public class AttackManager {
    private final Collection<ProxyChecker> proxyCheckers;
    private final GeoApi geoApi;

    private final BlacklistCheck blacklistCheck;
    private final GeographicalCheck geographicalCheck;
    private final NicknameCheck nicknameCheck;
    private final ProxyCheck proxyCheck;
    private final ReJoinCheck reJoinCheck;
    private final ServerListCheck serverListCheck;

    private int connectPerSecond = 0;
    private int totalBots = 0;
    private boolean attackMode = false;

    public AttackManager() {
        this.proxyCheckers = new HashSet<>();
        LibraryLoader.init();
        Configuration.load();
        Messages.load();
        StorageManager.load();

        this.blacklistCheck = new BlacklistCheck();
        this.geographicalCheck = new GeographicalCheck(this);
        this.nicknameCheck = new NicknameCheck();
        this.proxyCheck = new ProxyCheck(this);
        this.reJoinCheck = new ReJoinCheck(this);
        this.serverListCheck = new ServerListCheck(this);

        if (Configuration.advancedProxyChecker) {
            this.proxyCheckers.addAll(Configuration.proxyCheckers);
        }
        this.geoApi = new GeoApi("plugins/EpicGuard", Configuration.countryEnabled, Configuration.cityEnabled);
    }

    public Detection check(String address, String nickname) {
        return new Detection(address, nickname, this);
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

    public GeoApi getGeoApi() {
        return this.geoApi;
    }

    public void increaseConnectPerSecond() {
        this.connectPerSecond++;
    }

    public void setConnectPerSecond(int i) {
        this.connectPerSecond = i;
    }

    public void increaseBots() {
        this.totalBots++;
    }

    public boolean isUnderAttack() {
        return this.attackMode;
    }

    public int getConnectPerSecond() {
        return this.connectPerSecond;
    }

    public int getTotalBots() {
        return this.totalBots;
    }

    public Collection<ProxyChecker> getProxyCheckers() {
        return this.proxyCheckers;
    }

    public void setAttackMode(boolean bol) {
        this.attackMode = bol;
    }

    public void reset() {
        this.attackMode = false;
        this.totalBots = 0;
    }
}
