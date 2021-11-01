package dev.vitorpaulo.jda.dao

import dev.vitorpaulo.jda.model.EmoteAction
import net.dv8tion.jda.api.entities.Emoji

object EmoteDao {

	private val CONTEXTS = arrayListOf<EmoteAction>()

	operator fun get(id: String, user: String, emoji: Emoji) =
		CONTEXTS.firstOrNull { it.message.id == id && it.user == user && it.emoji == emoji }

	fun add(emoteContext: EmoteAction) = CONTEXTS.add(emoteContext)

	fun remove(id: String) = CONTEXTS.removeIf { it.message.id == id }

}