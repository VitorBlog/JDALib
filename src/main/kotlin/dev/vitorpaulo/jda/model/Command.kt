package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import java.io.File
import java.util.function.Consumer

open class Command {

    lateinit var user: User
    lateinit var message: Message
    lateinit var arguments: Array<String>
    lateinit var channel: TextChannel
    lateinit var guild: Guild
    lateinit var event: GuildMessageReceivedEvent

    open fun execute() {}

    fun reply(
        content: Any,
        file: File? = null,
        replaceMessage: Message? = null,
        vararg actionRow: ActionRow = arrayOf(),
        success: Consumer<Message> = Consumer { },
        error: Consumer<Throwable> = Consumer {
            reply(":dizzy_face: An error occurred.")
            it.printStackTrace()
        }
    ) {

        val messageBuilder = MessageBuilder()

        if (content is String) messageBuilder.setContent(content)
        if (content is MessageEmbed) messageBuilder.setEmbed(content)

        messageBuilder.setActionRows(actionRow.toList())

        val action = replaceMessage?.editMessage(messageBuilder.build()) ?: message.reply(messageBuilder.build())

        if (file != null) action.addFile(file)

        action.queue(success, error)

    }

    fun setup(event: GuildMessageReceivedEvent): Command {

        this.user = event.author
        this.message = event.message
        this.channel = event.channel
        this.guild = event.guild
        this.event = event
        this.arguments = event.message.contentRaw.split(" ").toTypedArray().let { it.copyOfRange(1, it.size) }

        return this

    }

}