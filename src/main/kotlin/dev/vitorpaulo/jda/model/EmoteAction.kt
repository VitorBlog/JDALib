package dev.vitorpaulo.jda.model

import dev.vitorpaulo.jda.JDALib
import dev.vitorpaulo.jda.dao.EmoteDao
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import java.util.function.Consumer

class EmoteAction(val message: Message, val user: String, val emoji: Emoji) {

	lateinit var consumer: Consumer<GuildMessageReactionAddEvent>

	fun queue(consumer: Consumer<GuildMessageReactionAddEvent>) {

		this.consumer = consumer
		EmoteDao.add(this)

		if (emoji.isUnicode) message.addReaction(emoji.name).queue()
		else message.addReaction(JDALib.JDA.getEmoteById(emoji.id) ?: return).queue()

	}

}