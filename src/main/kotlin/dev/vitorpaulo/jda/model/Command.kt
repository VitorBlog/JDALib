package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.requests.restaction.MessageAction
import java.io.File
import java.util.function.Consumer

open class Command(
    val slashCommand: CommandData? = null
) {

    lateinit var user: User
    lateinit var message: Message
    lateinit var arguments: Array<String>
    lateinit var channel: MessageChannel
    lateinit var guild: Guild
    lateinit var event: Event
    private var useSlash = false

    open fun execute() {}

    fun reply(
        content: Any,
        file: File? = null,
        replaceMessage: Message? = null,
        vararg actionRow: ActionRow = arrayOf(),
        success: Consumer<Message> = Consumer { },
        error: Consumer<Throwable> = Consumer {
            it.printStackTrace()
        }
    ) {

        val messageBuilder = MessageBuilder()

        if (content is String) messageBuilder.setContent(content)
        if (content is MessageEmbed) messageBuilder.setEmbed(content)
        if (content is EmbedBuilder) messageBuilder.setEmbed(content.build())

        messageBuilder.setActionRows(actionRow.toList())

        val action = when {
            replaceMessage != null -> replaceMessage.editMessage(messageBuilder.build())
            useSlash -> (event as SlashCommandEvent).reply(messageBuilder.build())
            channel.type == ChannelType.PRIVATE -> channel.sendMessage(messageBuilder.build())
            else -> message.reply(messageBuilder.build())
        }

        if (file != null && action is MessageAction) action.addFile(file)

        if (action is MessageAction) action.queue(success, error) else action.queue({}, error)

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

    fun openPrivateChannel() {

        channel = user.openPrivateChannel().complete()

    }

    fun setup(arguments: Array<String>, event: SlashCommandEvent): Command {

        this.user = event.user
        this.channel = event.textChannel
        this.guild = event.guild!!
        this.event = event
        this.arguments = arguments
        this.useSlash = true

        return this

    }

}