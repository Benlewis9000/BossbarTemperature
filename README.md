# BossbarTemperature
Spigot plugin that simulates temperatures via the BossBar. Native to Spigot 1.13.2, untested on other versions.

This project is actively maintained; upcoming features will include the ability to add different seasons, degrees of randomness to the daily weather, and a better algorithm for calculating temperatures (e.g. Bezier curves instead of sine wave transformation).

**Commands:**
  - /temp
    - Main command to configure/reload plugin
    - *bossbartemperature.admin*
    
**Config:**
```YAML
# Layout of bossbar message (colour codes are enabled)
# %celsius% and %fahrenheit% are replaced with calculated values
layout: "&bWinter &7| &f%celsius%C &7| &c%fahrenheit%F"
enabled: true
# Ticks to update temperature (default 5s)
# This can only be changed while the server is offline
tickrate: 100
# Min and max temperatures in celsius
min-temperature: 0
max-temperature: 30
# Hottest time of ingame day (in ticks, 0-24,000)
peak-time: 6000
# Name of world to activate temperatures on
world-name: "world"
```
    
**Note:** Pending the completion of PluginToolsAPI, this project is subject to a rewrite and will be rebranded as "Forecast".
