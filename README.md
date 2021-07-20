# EpicGuard [![GitHub stars](https://img.shields.io/github/stars/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/stargazers) [![GitHub forks](https://img.shields.io/github/forks/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/network) [![GitHub issues](https://img.shields.io/github/issues/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/issues) [![GitHub license](https://img.shields.io/github/license/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/blob/master/LICENSE)

<img align="right" src="https://i.imgur.com/3oehYSw.jpg" height="200" width="200">

EpicGuard is an antibot plugin for minecraft servers, supporting bukkit, bungeecord and velocity. Highly configurable and customizable.

**NOTE: The current code is under heavy refactoring, but it should be stable**
## Building
After cloning the repository, run the following command in the repository root:
```
./gradlew build
```
Or, if you are on Windows:
```
gradlew.bat build
```
The final jar file will be located in `build/libs/EpicGuard-<version>-all.jar`

## Some features
 * All checks can be configured as you want, you can make them checking every time, only during an attack or disable some of them completely.
 * Compatible with Spigot(+forks), BungeeCord(+forks), and Velocity.
 * Block VPNs and proxies, specify your own detection services.
 * Allow or block specific countries/cities.
 * Automatic whitelisting, so real players won't be affected during bot attack.
 * Attack status displayed on the action bar.
 * Filter unwanted console messages.
 * Throttle connections to the server.
 * Limit how many nicknames can be used by one IP address.
 * Force users to ping the server or add it to their server list.
 * Detect similar nicknames of connecting players.

**If you want to see more, go to the [Resource Page](https://www.spigotmc.org/resources/72369/).**

## Support
If you found an issue with the plugin, you can either go to the Issue Tracker, or join our discord server.
Click the discord logo to join the support server!  
[![Discord](https://discord.com/assets/94db9c3c1eba8a38a1fcf4f223294185.png)](https://discord.gg/VkfhFCv)
