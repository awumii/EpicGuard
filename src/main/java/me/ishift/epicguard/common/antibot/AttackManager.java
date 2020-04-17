package me.ishift.epicguard.common.antibot;

import me.ishift.epicguard.common.antibot.check.checks.BlacklistCheck;
import me.ishift.epicguard.common.antibot.check.checks.GeographicalCheck;
import me.ishift.epicguard.common.antibot.check.checks.NicknameCheck;
import me.ishift.epicguard.common.antibot.check.checks.ProxyCheck;
import me.ishift.epicguard.common.antibot.check.checks.ReJoinCheck;
import me.ishift.epicguard.common.antibot.check.checks.ServerListCheck;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.LibraryLoader;
import me.ishift.epicguard.common.util.GeoApi;

import java.util.Collection;
import java.util.HashSet;

public class AttackManager {
    private final Collection<ProxyChecker> proxyCheckers;
    private GeoApi geoApi;

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
        Configuration.load();
        Messages.load();
        LibraryLoader.init();
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
        if (Configuration.countryEnabled) {
            this.geoApi = new GeoApi("plugins/EpicGuard", true, Configuration.cityEnabled);
        }
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
