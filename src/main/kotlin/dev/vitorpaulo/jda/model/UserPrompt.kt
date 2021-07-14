package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import java.util.function.Consumer

class UserPrompt(val user: User, val textChannel: TextChannel) {

    val id = "${user.id}:${textChannel.id}"
    var validator: () -> Boolean = { true }
    var cancelSequences = arrayListOf<String>()
    var invalidCallback: Consumer<Message>? = null
    var canceledCallback: Consumer<Message>? = null
    var successCallback: Consumer<Message>? = null

    fun validator(validator: () -> Boolean) = apply { this.validator = validator; return this }

    fun addCancelSequence(string: String) = apply { this.cancelSequences.add(string); return this }

    fun cancelSequences(cancelSequences: ArrayList<String>) = apply { this.cancelSequences = cancelSequences; return this }

    fun invalidCallback(invalidCallback: Consumer<Message>) = apply { this.invalidCallback = invalidCallback; return this }

    fun canceledCallback(canceledCallback: Consumer<Message>) = apply { this.canceledCallback = canceledCallback; return this }

    fun successCallback(successCallback: Consumer<Message>) = apply { this.successCallback = successCallback; return this }

    fun queue() {



    }

}