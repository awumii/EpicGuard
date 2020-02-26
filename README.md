![GitHub release (latest by date)](https://img.shields.io/github/v/release/PolskiStevek/EpicGuard) ![GitHub issues](https://img.shields.io/github/issues/PolskiStevek/EpicGuard)
## EpicGuard 
Main plugin overview page on spigotmc: https://www.spigotmc.org/resources/epicguard-advanced-server-protection-antibot-more.72369/  

Securing your server is a very important thing, and this resource contains many features, keep your server clean and safe. EpicGuard can protect your server from every kind of bots and has almost no performance impact. In contrast from other antibots/these type plugins, its completely free and support is provided very fast, using our discord server. Full list of features is below.  

Supported versions: 1.8*, 1.12, 1.13, 1.14, 1.15(Spigot), BungeeCord (Waterfall, Travertine etc.).  
If you have bungeecord network, use this plugin only on bungeecord, unless you want to use "command protections/anti backdoor" modules.

## Maven dependency
If you want to use EpicGuard as dependency for your project, add this to pom.xml  
JitPack repository:
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
EpicGuard artifact:
```
<dependency>
    <groupId>com.github.PolskiStevek</groupId>
    <artifactId>EpicGuard</artifactId>
    <version>CHECK_LATEST_VERSION</version>
    <scope>provided</scope>
</dependency>
```
## Features
* BungeeCord and Spigot (1.8.x - 1.15.x) support.
* Highly configurable and customizable.
* Almost no performance impact.
* Blocking VPN services and proxies.
* Country filter - allow or block specified countries.
* Fast attack detection, you can configure on which connect/ping speed attack will be detected.
* Automatic whitelisting, so real players won't be detected by antibot, and they can join while bots can't.
* Firewall commands support.
* Anti ForceOP/Backdoor - only players added to the exclusive list can have operator permissions (it can also protect from PermissionsEx "*" permission).
* See every player's address history (if you enabled this module).
* Block commands that you don't want players/staff to execute.
* Configure a list of allowed commands that can regular players execute.
* Display custom tab-completions (on /<tab>), or completely disable tab completion.
* Manage your server using GUI instead of annoying commands!
* Filter console messages.
* Cloud blacklist synchronizing.
* ActionBar and/or Title notifications.