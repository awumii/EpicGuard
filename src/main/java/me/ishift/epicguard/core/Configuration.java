package me.ishift.epicguard.core;

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgName;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Collections;
import java.util.List;

@CfgClass(name = "Configuration")
@CfgDelegateDefault("{new}")
public class Configuration {

    @CfgComment("Country check will filter countries your players can connect from.")
    @CfgComment("Check mode will define, when this check will work.")
    @CfgComment("NEVER - check is disabled.")
    @CfgComment("ALWAYS - check will perform on every player.")
    @CfgComment("ATTACK - check will perform only during bot attack.")
    @CfgComment("")
    @CfgName("country-check-mode")
    public String countryCheckMode = "NEVER";

    @CfgComment("This will define if country-check should be blacklist or whitelist.")
    @CfgComment("BLACKLIST - countries below are blocked")
    @CfgComment("WHITELIST - only countries below are allowed")
    @CfgName("country-check-type")
    public String countryCheckType = "BLACKLIST";

    @CfgComment("List of country codes: https://dev.maxmind.com/geoip/legacy/codes/iso3166/")
    @CfgName("country-check-countries")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> countryCheckValues = Collections.singletonList("US");

    @CfgComment("If some player's city is listed here, he will be blacklisted.")
    @CfgName("city-blacklist")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> cityBlacklist = Collections.singletonList("ExampleCity");
}
