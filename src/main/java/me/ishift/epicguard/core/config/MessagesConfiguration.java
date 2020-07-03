package me.ishift.epicguard.core.config;

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgName;
import org.diorite.cfg.annotations.defaults.CfgDelegateDefault;

import java.util.Arrays;
import java.util.List;

@CfgClass(name = "MessagesConfiguration")
@CfgDelegateDefault("{new}")
public class MessagesConfiguration {

    @CfgName("kick-message-geo")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageGeo = Arrays.asList(
            "&8[&aEpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYour country/city is not allowed on this server.");

    @CfgName("kick-message-blacklist")
    @CfgCollectionStyle(CfgCollectionStyle.CollectionStyle.ALWAYS_NEW_LINE)
    public List<String> kickMessageBlacklist = Arrays.asList(
            "&8[&aEpicGuard&8]",
            "&7You have been kicked by our &aantibot protection&7.",
            "&7Details: &cYour IP address is blacklisted on this server.");
}
