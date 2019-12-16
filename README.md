DOWNLOAD BUILDS HERE: https://www.spigotmc.org/resources/epicguard-antibot-anti-forceop-server-protection.72369/
# About Plugin

> EpicGuard is a plugin, that can protect your server from bots, hackers or griefers!
> Block countries, block proxy, and VPN, automatic firewall commands, blocking fast and slow connections, see if your server is attacked using fancy notifications. Custom blacklist and whitelist system.
> See basic information about players, see their country, and IP history. When a player joins with a new IP, he is logged and the staff is informed.
> You can see opped players list, and when they were last online. You can configure everything - plugin messages, kick messages, permission base, prefix, attack speed options, and more!
> This plugin includes its own log system - messages are logged both in the console and in logs.txt file.


# Plugin Features

    AntiBot features:
        Proxy, VPN blocking.
        Block fast connections to the server.
        Block fast pings to the server
        Block fast joins to the server.
        Block specific countries.
        Force players (bots) to rejoin.
        Setup Country Whitelist or Blacklist
        Automatic Whitelisting
        Whitelist and Blacklist IP system.
        Firewall commands support.
    AntiExploit (very simple for now) - block book edit (configurable max pages limit).
    Anti ForceOP - only players added to the special list can have operator permissions.
    Player IP History - see player's IP history (can be disabled).
    Block commands, you don't want players or staff to execute.
    Configure a list of allowed commands that can normal players execute.
    Manage your server using GUI instead of boring commands!
    ActionBar and Title notifications (for antibot).
    Customizable messages file.
    Custom logs system.
    Custom error catching system.
    Everything is customizable (except GUI's).
    100% Free!

# Commands & Permissions
Commands:

    /guard - opens plugin GUI.
    /guard help - list of commands
    /guard whitelist <adress> - manually add specified adress to plugin whitelist.
    /guard blacklist <adress> - manually add specified adress to plugin blacklist.
    /guard status - toggle antibot notifications.
    /guard op - list of opped players.
    /guard player <player> - basic information about player.
    /guard reload - reload config.

Permissions:

    epicguard.admin - basic permission for commands, and alerts.
    epicguard.protection.notify - permission to see alerts from OP-protection.
    epicguard.exploit.notify - permission for anti-exploit notifications (when someone is kicked for exploit).


# Configuration and Screenshots
[SPOILER="Config File"][code=YAML]
#
#    ███████╗██████╗ ██╗ ██████╗ ██████╗ ██╗   ██╗ █████╗ ██████╗ ██████╗
#    ██╔════╝██╔══██╗██║██╔════╝██╔════╝ ██║   ██║██╔══██╗██╔══██╗██╔══██╗
#    █████╗  ██████╔╝██║██║     ██║  ███╗██║   ██║███████║██████╔╝██║  ██║
#    ██╔══╝  ██╔═══╝ ██║██║     ██║   ██║██║   ██║██╔══██║██╔══██╗██║  ██║
#    ███████╗██║     ██║╚██████╗╚██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝
#    ╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝
#
# Thank you for downloading my plugin!
# SpigotMC Link: https://www.spigotmc.org/resources/%E2%9C%A9-epicguard-1-8-1-14-antibot-antivpn-proxy-spigot-bungeecord-staff-protection-tools-%E2%9C%A9.72369/
# My Discord: PolskiStevek#2198
updater: true

###############################
##           Countries       ##
###############################

countries:
  # This has three values:
  # WHITELIST = countries listed below CAN join, every other can't.
  # BLACKLIST = countries listed below CAN'T join, every other can.
  # DISABLED = disable this module.
  mode: WHITELIST
  # List of country codes:
  # https://dev.maxmind.com/geoip/legacy/codes/iso3166/
  list:
    - US
    - GB
    - DE

###############################
##           AntiBot         ##
###############################

antibot:
  # You can fuly disable ALL antibot modules if you don't want it.
  # Disabling antibot will not disable country checking (look above)
  enabled: true
  # HIGHLY RECOMMENDED: Register on https://proxycheck.io/dashboard and get a free API KEY!
  # Then replace the URL "http://proxycheck.io/v2/{IP}&vpn=1" to this:
  # "http://proxycheck.io/v2/{IP}?key=API_KEY&vpn=1"
  checkers:
    '1':
      adress: "http://proxycheck.io/v2/{IP}&vpn=1"
    '2':
      adress: "http://www.stopforumspam.com/api?ip={IP}"
    '3':
      adress: "https://ip.teoh.io/{IP}"
    # What should the return message of each page contain?
    responses:
      - "yes"
      - "true"
      - "Bad"

###############################
##     Server Protection     ##
###############################

ip-history:
  enabled: true

op-protection:
  enabled: false
  # Permission to see alert: epicguard.protection.notify
  alert: "&cPlayer &6{PLAYER} &chas been banned for Force-OP."
  command: "ban {PLAYER} Force-OP"
  # List of players who can have OP
  list:
    - "Admin"

# If someone tries to execute command listed here,
# event will be canceled, and the player will see a custom message
# defined in messages.yml

command-protection:
  enabled: false
  list:
    - "/op"
    - "/deop"

# If someone tries to execute a command not listed
# in this module, and does not have bypass permission,
# event will be canceled, and custom message will be shown

allowed-commands:
  enabled: false
  # Permission for bypass this module.
  # Everyone with the permission below can use any command.
  bypass: "epicguard.command.bypass"
  # If someone execute command not listed below,
  # without permission to bypass, will get custom message
  # defined in messages.yml
  list:
    - /msg
    - /r
    - /reply
    - /tp
    - /tpa
    - /v
    - /vanish
    - /plot
    - /fly
    - /chat
    - /mute
    - /warn
    - /kick
    - /ban
    - /tempban
    - /unwarn
    - /god
    - /heal
    - /feed
    - /helpop
    - /ec
    - /enderchest
    - /home
    - /back
    - /sethome
    - /warp
    - /tpaccept
    - /tpdeny
    - /ignore
    - /spawn
    - /hub
    - /lobby
    - /report
    - /pay
    - /ping
    - /mail
    - /kit
    - /bal
    - /balance
    - /money
    - /afk
    - /sell

###############################
##       AntiExploit         ##
###############################

anti-exploit:
  # Set this to true to enable all features of this section.
  enable: false
  # Prints all received packets to console!
  kick-message: "&cKicked for using exploit ({EXPLOIT})!"
  staff-notification: "&cPlayer {PLAYER} has been detected for using exploit ({EXPLOIT})!"
  modules:
    # Max amount of pages, a player can send over 47k pages to lag the server.
    max-book-pages: 50


###############################
##           Advanced        ##
###############################

speed:
  # How many connections per second should be made, to block connections to the server.
  # Whitelisted players still can join, if CPS is below this value, connection blocking will be disabled.
  # If deny-join is triggered, Proxy/VPN checking is skipped.
  connection: 10
  # How many pings per second should be made to block connections to the server.
  ping-speed: 15
  # How many successful joins per second should be made to block connections to the server
  join-speed: 5
  # Time (in ticks) before the antibot will disable.
  attack-timer-reset: 800

auto-whitelist:
  # Enable automatic player whitelisting.
  # When a player is whitelisted, all checks are skipped (like a Proxy check).
  # Whitelisted players can join even when ATTACK_MODE is activated.
  enabled: true
  # How much time (in ticks), the player should be online to be added to the whitelist.
  # 20 ticks = 1 second
  time: 2400


###############################
##           Firewall        ##
###############################

firewall:
  # Don't use on free / shared hosting!
  # You need private machine with IPTables (or something else) installed.
  enabled: false
  # Executed in /guard blacklist <player> OR while player was detected as a BOT
  command-blacklist: "iptables -A INPUT -s {IP} -j DROP"
  # Executed in /guard whitelist <player>
  command-whitelist: "iptables -D INPUT -s {IP} -j DROP"
[/code][/SPOILER]

[SPOILER="Messages File"]
[code=YAML]configVersion: "2-1-0"
prefix: "&8[&bEpicGuard&8] "
actionbar:
  attack: "&f{NICK} &7[&f{IP}&7] failed &8» &c{DETECTION} &8| &c{CPS}&7/&ccps"
  no-attack: "&7No Attack... {ANIM} &8| &a{CPS}&7/&acps"
attack-title:
  # {MAX} - Number of blocked bots during last attack.
  # {CPS} - Connections Per Second.
  title: "&6⚠ &cServer is attacked ({MAX})! &6⚠"
  subtitle: "&7Attack speed: &a{CPS}&7/&asec&7!"
other:
  history-new: "&7Player &e{NICK} &7connected with &fNEW&7 IP Adress &8(&6{IP}&8)&7!"
  no-permission: "&cYou don't have permission! If you think this is an issue, report this to administrators."
  # This option is only one here
  # without "prefix" added before it!
  not-allowed-command: "&fUnknown command. Type /help for help."
  blocked-command: "&cThis command is blocked!"
kick-messages:
  proxy:
    - "&8▪ &cEpicGuard AntiBot &8▪"
    - " "
    - "&7Our systems have detected, that you are using Proxy or VPN."
    - "&7Please disable VPN programs, and join server."
    - " "
    - "&7If you think this is an issue, contact our staff."
  blacklist:
    - "&8▪ &cEpicGuard AntiBot &8▪"
    - " "
    - "&7Your IP has been blacklisted on this server."
    - " "
    - "&7If you think this is an issue, contact our staff."
  attack:
    - "&8▪ &cEpicGuard AntiBot &8▪"
    - " "
    - "&7Our server is being attacked."
    - "&7Please wait some minutes before connecting."
    - " "
    - "&7If you think this is an issue, contact our staff."
  country:
    - "&8▪ &cEpicGuard AntiBot &8▪"
    - " "
    - "&7Your country is not allowed on this server."
    - " "
    - "&7If you think this is an issue, contact our staff."[/code]
[/SPOILER]

[SPOILER="Screenshots"]
[ATTACH=full]464898[/ATTACH] [ATTACH=full]469404[/ATTACH] [ATTACH=full]466135[/ATTACH] [ATTACH=full]466136[/ATTACH] [/SPOILER]

# Other Information

If the plugin can't download the GeoIP database, download it here and put it in the plugins/EpicGuard folder.
If the plugin can't download messages.yml file, download it here and put it in the plugins/EpicGuard folder.

If you found a bug, or want some feature, contact me!

    Use this resource's "Discussion" tab.
    My discord account: PolskiStevek#2198.
    Github issue tracker: https://github.com/PolskiStevek/EpicGuard/issues
    Or just send me a message on spigotmc.

    This plugin requires Spigot or PaperSpigot, version from 1.8 to 1.15, Craftbukkit is not supported.
    BungeCord is supported, but most functions from the spigot are not included yet.

# Plugin Statistics
https://bstats.org/plugin/bukkit/EpicGuard
