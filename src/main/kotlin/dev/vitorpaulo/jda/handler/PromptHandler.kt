package dev.vitorpaulo.jda.handler

import dev.vitorpaulo.jda.dao.PromptDao
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class PromptHandler : ListenerAdapter() {

    override
    fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        val prompt = PromptDao["${event.author.id}:${event.channel.id}"] ?: return
        val content = event.message.contentRaw

        if (prompt.cancelSequences.any { it.equals(content, true) }) {

            PromptDao.remove(prompt.id)
            prompt.canceledCallback?.accept(event.message)
            return

        }

        if (!prompt.validator.invoke()) {

            PromptDao.remove(prompt.id)
            prompt.invalidCallback?.accept(event.message)
            return

        }

        PromptDao.remove(prompt.id)
        prompt.successCallback?.accept(event.message)

    }

}