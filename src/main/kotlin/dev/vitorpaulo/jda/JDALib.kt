package dev.vitorpaulo.jda

import dev.vitorpaulo.jda.dao.CommandDao
import dev.vitorpaulo.jda.handler.*
import dev.vitorpaulo.jda.model.Command
import dev.vitorpaulo.jda.process.CommandProcess
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.ListenerAdapter

object JDALib {

    lateinit var JDA: JDA
    private lateinit var builder: JDABuilder

    var delay = 5
    var prefix = ";"
    var bypassList = arrayListOf("447420204453068801")
    var permissionMessage = ":no_entry_sign: You doesn't have the permission for that command."
    var delayMessage = ":clock4: Hey my buddy you need to wait `{time} seconds` to use a command again."

    fun create(token: String): JDALib {

        builder = JDABuilder
            .createDefault(token)
            .addEventListeners(CommandHandler())
            .addEventListeners(ButtonHandler())
            .addEventListeners(EmoteHandler())
            .addEventListeners(PromptHandler())
            .addEventListeners(SlashCommandHandler())

        CommandProcess.load()

        return this

    }

    fun addEventListener(listener: ListenerAdapter): JDALib {

        builder.addEventListeners(listener)
        return this

    }

    fun addCommand(command: Command): JDALib {

        CommandDao.add(command)
        return this

    }

    fun setActivity(activity: Activity): JDALib {

        builder.setActivity(activity)
        return this

    }

    fun setPrefix(string: String): JDALib {

        prefix = string
        return this

    }

    fun setPermissionMessage(string: String): JDALib {

        permissionMessage = string
        return this

    }

    fun setDelayMessage(string: String): JDALib {

        delayMessage = string
        return this

    }

    fun setDelay(int: Int): JDALib {

        delay = int
        return this

    }

    fun build(): JDA {

        JDA = builder.build().awaitReady()

        val commandList = JDA.updateCommands()

        CommandDao.COMMANDS.filter { it.slashCommand != null }.forEach { command ->
            commandList.addCommands(command.slashCommand)
        }

        commandList.queue()

        return JDA

    }

}