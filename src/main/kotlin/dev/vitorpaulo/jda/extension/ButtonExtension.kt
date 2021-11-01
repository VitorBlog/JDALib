package dev.vitorpaulo.jda.extension

import dev.vitorpaulo.jda.dao.ButtonDao
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.Button
import java.util.function.Consumer

object ButtonExtension {

	fun Button.callback(consumer: Consumer<ButtonClickEvent>): Button {

		ButtonDao.add(this.id ?: "404", consumer)
		return this

	}

}