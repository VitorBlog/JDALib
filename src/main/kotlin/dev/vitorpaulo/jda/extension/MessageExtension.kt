package dev.vitorpaulo.jda.extension

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User

object MessageExtension {

	fun Message.getTarget(): User? {

		if (this.mentionedUsers.isNotEmpty()) return this.mentionedUsers.first()

		if (this.contentRaw.split(" ").size > 1) {

			val text = this.contentRaw.split(" ")[1]

			return this.jda.users.firstOrNull { it.id == text || it.name.equals(text, true) }

		}

		return null

	}

}