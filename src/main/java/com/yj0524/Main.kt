package com.yj0524

import org.bukkit.plugin.java.JavaPlugin
import io.github.monun.kommand.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Main : JavaPlugin() {

    override fun onEnable() {
        getLogger().info("Plugin Enabled")

        kommandsLoad()
    }

    override fun onDisable() {
        getLogger().info("Plugin Disabled")
    }

    fun kommandsLoad() {
        val string = KommandArgument.string(StringType.QUOTABLE_PHRASE)

        kommand {
            register("titles") {
                requires {
                    isOp
                }
                then("players" to players()) {
                    executes {
                        sender.sendMessage("§c사용법 : /titles <players> <title> [subtitle]")
                    }
                    then("title" to string) {
                        executes {
                            val players: Collection<Player> by it
                            val title: String by it
                            players.forEach { player ->
                                val title = title.replace("&", "§")
                                player.sendTitle(title, null)
                            }
                        }
                        then("subtitle" to string) {
                            executes {
                                val players: Collection<Player> by it
                                val title: String by it
                                val subtitle: String by it
                                players.forEach { player ->
                                    val title = title.replace("&", "§")
                                    val subtitle = subtitle.replace("&", "§")
                                    player.sendTitle(title, subtitle)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
