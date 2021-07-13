package dev.vitorpaulo.jda.dao

import dev.vitorpaulo.jda.model.Command
import dev.vitorpaulo.jda.model.CommandContext
import dev.vitorpaulo.jda.model.CommandInfo

object CommandDao {

    private val COMMANDS = arrayListOf<CommandContext>()

    operator fun get(string: String) = COMMANDS.firstOrNull { it.checkAliases(string.lowercase()) }

    fun add(command: Command) {

        val annotation = command::class.java.annotations.firstOrNull() ?: return
        if (annotation !is CommandInfo) return

        COMMANDS.add(CommandContext(annotation, command))

    }

}