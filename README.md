# EpicGuard [![GitHub stars](https://img.shields.io/github/stars/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/stargazers) [![GitHub forks](https://img.shields.io/github/forks/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/network) [![GitHub issues](https://img.shields.io/github/issues/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/issues) [![GitHub license](https://img.shields.io/github/license/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/blob/master/LICENSE)

NOTE: The current code is under heavy refactoring.  
Releases may be outdated, use [latest builds](https://github.com/xxneox/EpicGuard/actions)

## Supported platforms
* [Paper 1.16.5+](https://papermc.io/)
* [Velocity 3.0.0+](https://velocitypowered.com/)
* BungeeCord (use latest [Waterfall](https://papermc.io/downloads#Waterfall))
* Java 16 is required.

## Features
* A total of 8 configurable antibot checks:
  * Geographical check - country/city blacklist or whitelist.
  * VPN/Proxy check - configurable services and caching.
  * Nickname check - block certain nickname patterns using regex.
  * Reconnect check - require re-joining the server with an identical pair of address and nickname.
  * Server list check - require pinging the server before connecting (adding it to the server list).
  * Settings check - make sure that player sends a settings packet after joining (vanilla client behaviour).
  * Lockdown - temporarily block incoming connections if there are too many of them.
  * Name similiarity check (BETA)
  * Account limit.
* SQLite/MySQL support.
* Live actionbar statistics. 
* Automatic whitelisting.
* Console filter.

## Support
If you found an issue with the plugin, you can either create a new [Issue](https://github.com/xxneox/EpicGuard/issues), or join the discord support server.
[![Discord](https://discord.com/assets/94db9c3c1eba8a38a1fcf4f223294185.png)](https://discord.gg/VkfhFCv)
