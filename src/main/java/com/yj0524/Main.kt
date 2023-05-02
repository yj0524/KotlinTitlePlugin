package com.yj0524

import io.github.monun.kommand.KommandArgument
import io.github.monun.kommand.StringType
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

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
                                val title = replaceAmpersand(title)
                                player.sendTitle(title, null)
                            }
                        }
                        then("subtitle" to string) {
                            executes {
                                val players: Collection<Player> by it
                                val title: String by it
                                val subtitle: String by it
                                players.forEach { player ->
                                    val title = replaceAmpersand(title)
                                    val subtitle = replaceAmpersand(subtitle)
                                    player.sendTitle(title, subtitle)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun replaceAmpersand(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] == '&' && i + 1 < input.length) {
                val c = input[i + 1]
                if (Character.isLetterOrDigit(c) && "0123456789abcdefklmnor".contains(c, ignoreCase = true)) {
                    sb.append("§")
                } else {
                    sb.append("&")
                }
            } else {
                sb.append(input[i])
            }
            i++
        }
        return sb.toString()
    }
}
