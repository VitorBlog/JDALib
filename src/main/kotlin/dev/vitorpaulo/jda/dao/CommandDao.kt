package dev.vitorpaulo.jda.dao

import dev.vitorpaulo.jda.model.Command
import dev.vitorpaulo.jda.model.CommandContext
import dev.vitorpaulo.jda.model.CommandInfo

object CommandDao {

	internal val COMMANDS = arrayListOf<CommandContext>()

	operator fun get(string: String, slash: Boolean = false) =
		COMMANDS.firstOrNull {
			(!slash && it.checkAliases(string))
					|| (slash && it.slashCommand?.name?.equals(string, true) == true)
		}

	internal fun add(command: Command) {

		val annotation = (command::class.java.annotations.firstOrNull { it is CommandInfo } ?: return) as CommandInfo

		COMMANDS.add(
			CommandContext(
				annotation,
				command
			)
		)

	}

}