package dev.vitorpaulo.jda.handler

import dev.vitorpaulo.jda.dao.EmoteDao
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EmoteHandler : ListenerAdapter() {

	override
	fun onMessageReactionAdd(event: MessageReactionAddEvent) {

		val emote = EmoteDao[
				event.messageId,
				event.userId,
				if (event.reactionEmote.isEmote)
					Emoji.fromEmote(event.reactionEmote.emote)
				else
					Emoji.fromUnicode(event.reactionEmote.emoji)
		] ?: return

		emote.consumer.accept(event)
		EmoteDao.remove(event.messageId)

	}

}
