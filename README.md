# EpicGuard
⚡️ Download builds here: https://www.spigotmc.org/resources/%E2%9A%A1%EF%B8%8F-epicguard-1-8-1-14-antibot-country-blocker-staff-protection-tools-%EF%B8%8F%E2%9A%A1%EF%B8%8F.72369/ 
![alt text](http://epicmc.cba.pl/cloud/uploads/Bez%20nazwy.png)
# About Plugin

EpicGuard is a plugin that I originally created for my server to better protect it against bots, griefers, and administration abusing its permissions. This plugin is not fully completed, but is functional.

If you found a bug, or want some feature, contact me!
My discord account: PolskiStevek#2198.
Or just send me message on spigotmc.
Discord server: https://discord.gg/Jk3M8yy
You should edit config after first run, for example i reccomend you to get a free api key from checkproxy.io

# Commands & Permissions

Commands:
/guard - main plugin command, informations about plugin and server, list of commands.
/guard whitelist <adress> - manually add specified adress to plugin whitelist.
/guard blacklist <adress> - manually add specified adress to plugin blacklist.
/guard status - toggle antibot notifications.
/guard op - list of opped players.
/guard antibot - antibot status, blacklisted / whitelisted ips.
/guard player <player> - basic information about player.
/guard reload - reload config.
Aliases: /eg, /ab, /epicguard.
Permisssions:
epicguard.admin - basic permission for commands, and alerts.

# Features

> AntiBot
Block countries, block proxy and vpn, automatic firewall commands, blocking fast and slow connections, see if your server is attacked using fancy notifications. Custom blacklist and whitelist system.

> Player Information & Security
See basic informations about players, see their country, and ip history. When player joins with new ip, he is logged and staff is informed. You can see opped players list, and when they were last online.

> Configurable
You can configurate everything - plugin messages, kick messages, permission base, prefix, attack speed options, and more!

> Logs
This plugin includes its own log system - messages are logged both in console and in logs.txt file. Example log:

> Dynamic GUI
Manage your server's protection using fancy, dynamically updated GUI Menu!

# Informations

If plugin can't download GeoIP database, download it here and put it in the plugins/EpicGuard folder.
Plugin requires Spigot or PaperSpigot, version from 1.8 to 1.14, Craftbukkit is not supported.
BungeCord is supported, but most functions from spigot is not included yet.
Do not decompile or deobfuscate plugin. If you want new feature, join my discord, if you really need source code, contact me!
