package dev.vitorpaulo.jda.handler

import dev.vitorpaulo.jda.JDALib
import dev.vitorpaulo.jda.dao.CommandDao
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import java.util.*

class SlashCommandHandler : ListenerAdapter() {

	override
	fun onSlashCommand(event: SlashCommandEvent) {

		event.guild ?: return

		val command = CommandDao[event.name, true] ?: return
		val arguments = arrayListOf<String>()

		event.options.forEach {
			arguments.add(
				when (it.type) {

					OptionType.STRING, OptionType.INTEGER,
					OptionType.BOOLEAN, OptionType.UNKNOWN -> it.asString
					OptionType.CHANNEL -> it.asGuildChannel.asMention
					OptionType.USER -> it.asUser.asMention
					OptionType.ROLE -> it.asRole.asMention
					else -> it.asString

				}
			)
		}

		if (!command.checkPermissions(event.member!!)) {

			event.reply(
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

		Thread { command.clazz::class.java.newInstance().setup(arguments.toTypedArray(), event).execute() }.start()

	}

}