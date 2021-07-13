package dev.vitorpaulo.jda.handler

import dev.vitorpaulo.jda.JDALib
import dev.vitorpaulo.jda.dao.CommandDao
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

class CommandHandler : ListenerAdapter() {

    private val delay = hashMapOf<String, Instant>()

    override
    fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        val content = event.message.contentRaw
        if (!content.startsWith(JDALib.prefix)) return

        val label = content.split(" ").first().removePrefix(JDALib.prefix)
        val command = CommandDao[label] ?: return

        if (!command.checkPermissions(event.member!!)) {

            event.channel.sendMessage(
                JDALib.permissionMessage
                    .replace("{permissions}",
                        command.permissions.joinToString {
                            it.getName().lowercase()
                                .replaceFirstChar { char ->
                                    if (char.isLowerCase()) char.titlecase(Locale.getDefault())
                                    else char.toString()
                            }
                        }
                    )
            ).queue(); return

        }

        if (delay.containsKey(event.author.id)) {

            val difference = Duration.between(delay[event.author.id], Instant.now()).seconds

            if (difference < JDALib.delay) {

                event.channel.sendMessage(
                    JDALib.delayMessage
                        .replace("{time}", (JDALib.delay - difference).toString())
                ).queue()
                return

            } else {

                delay.remove(event.author.id)

            }

        }

        Thread { command.clazz::class.java.newInstance().setup(event).execute() }.start()

        if (!JDALib.bypassList.contains(event.author.id)) delay[event.author.id] = Instant.now()

    }

}