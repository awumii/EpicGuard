# EpicGuard [![GitHub stars](https://img.shields.io/github/stars/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/stargazers) [![GitHub forks](https://img.shields.io/github/forks/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/network) [![GitHub issues](https://img.shields.io/github/issues/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/issues) [![GitHub license](https://img.shields.io/github/license/xxneox/EpicGuard)](https://github.com/xxneox/EpicGuard/blob/master/LICENSE) [![Java CI](https://github.com/xxneox/EpicGuard/actions/workflows/gradle.yml/badge.svg)](https://github.com/xxneox/EpicGuard/actions/workflows/gradle.yml)

Releases may be outdated, use [latest builds](https://github.com/xxneox/EpicGuard/actions)

## Supported platforms
* [Paper 1.16.5+](https://papermc.io/)
* [Velocity 3.0.0+](https://velocitypowered.com/)
* BungeeCord (you have to use [Waterfall](https://papermc.io/downloads#Waterfall))
* Java 16+ is required.

## Using EpicGuard in your project:
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
<summary>Accessing the API</summary>
Make sure that EpicGuard is fully loaded before your plugin.

[Click to see the API class](https://github.com/xxneox/EpicGuard/blob/master/core/src/main/java/me/xneox/epicguard/core/EpicGuardAPI.java)

```java
// Importing the API class.
import me.xneox.epicguard.core.EpicGuardAPI;

// Accessing the EpicGuardAPI instance.
EpicGuardAPI api = EpicGuardAPI.INSTANCE;

// Example #1: using the AttackManager class:
AttackManager attackManager = api.attackManager();

boolean isUnderAttack = attackManager.isUnderAttack(); // checking if server is under attack.
int cps = attackManager.connectionCounter(); // checking current connections per second.        
        
api.attackManager().attack(false); // disabling the attack mode.
api.attackManager().resetConnectionCounter() // reset the connection counter.
        
// Example #3: checking user's country:
String countryId = api.geoManager().countryCode("127.0.0.1") 
```
</details>

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
