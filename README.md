# 🛡 EpicGuard [![GitHub stars](https://img.shields.io/github/stars/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/stargazers) [![GitHub forks](https://img.shields.io/github/forks/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/network) [![GitHub issues](https://img.shields.io/github/issues/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/issues) [![GitHub license](https://img.shields.io/github/license/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/blob/master/LICENSE) [![Java CI](https://github.com/xxneox/EpicGuard/actions/workflows/gradle.yml/badge.svg)](https://github.com/xxneox/EpicGuard/actions/workflows/gradle.yml)
A simple AntiBot plugin for newest Minecraft versions.

## ✅ Supported platforms
* [Paper 1.16.5+](https://papermc.io/) *(all paper forks are supported)*
* [Velocity 3.0.0+](https://velocitypowered.com/)
* BungeeCord *([Waterfall](https://papermc.io/downloads#Waterfall) required)*
* Java **16**+ is required.

## ✨ Features
* A total of **8** configurable antibot checks:
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

## 📚 Commands & Permissions
To be able to use commands, give yourself the **epicguard.admin** permission.  
On different platforms there are additional aliases available, such as **/guardvelocity** or **/epicguardpaper**

| Command                                      | Description                                                            |
|----------------------------------------------|------------------------------------------------------------------------|
| /guard help                                  | Displays all available commands.                                       |
| /guard reload                                | Reloads config and messages.                                           |
| /guard whitelist <add/remove> <nick/address> | Whitelist/unwhitelist an address or nickname.                          |
| /guard blacklist <add/remove> <nick/address> | Blacklist/unblacklist an address or nickname.                          |
| /guard analyze <nick/address>                | Displays detailed information about the specified address or nickname. |
| /guard status                                | Toggles live attack information on actionbar.                          |
| /guard save                                  | Forces save to the database.                                           |

## 💻 Contributing
We are open to contributions! Look for some "TODO"s in the code, there's always some work to do.  
Because of a lack of time and motivation, no new features are being implemented in the project, aside from bug fixes and code improvements. Maybe you have an idea that's worth implementing?  
You can join our Discord server for better communication.

## 🔧 Using EpicGuard in your project:
<details>
<summary>Gradle (Groovy)</summary>

```groovy
repositories {
    maven {
      url = 'https://jitpack.io'
    }
}

dependencies {
    compileOnly 'com.github.xxneox:EpicGuard:[VERSION OR COMMIT ID HERE]'
}
```
</details>

<details>
<summary>Gradle (Kotlin)</summary>

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.xxneox:EpicGuard:[VERSION OR COMMIT ID HERE]")
}
```
</details>

<details>
<summary>Maven</summary>

```xml
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>

<dependencies>
    <dependency>
       <groupId>com.github.xxneox</groupId>
       <artifactId>EpicGuard</artifactId>
       <version>[VERSION OR COMMIT ID HERE]</version>
   </dependency>
</dependencies>
```
</details>

<details>
<summary>Using the API</summary>
Make sure that EpicGuard is fully loaded before your plugin.

[Click to see the API class](https://github.com/xxneox/EpicGuard/blob/master/core/src/main/java/me/xneox/epicguard/core/EpicGuardAPI.java)

```java
// Importing the API class.
import me.xneox.epicguard.core.EpicGuardAPI;
import me.xneox.epicguard.core.manager.AttackManager;

public class EpicGuardAPIExample {
  // Accessing the EpicGuardAPI instance.
  EpicGuardAPI api = EpicGuardAPI.INSTANCE;

  // Obtaining the AttackManager instance:
  AttackManager attackManager = api.attackManager();

  // Checking if server is under attack.
  boolean isUnderAttack = attackManager.isUnderAttack();

  // checking current connections per second.
  int cps = attackManager.connectionCounter();
  
  // Checking user's country:
  String countryId = api.geoManager().countryCode("127.0.0.1");
}
```
</details>

## ❓ Support
If you found an issue with the plugin, you can either create a new [Issue](https://github.com/xxneox/EpicGuard/issues), or join the discord support server.
[![Discord](https://discord.com/assets/94db9c3c1eba8a38a1fcf4f223294185.png)](https://discord.gg/VkfhFCv)
