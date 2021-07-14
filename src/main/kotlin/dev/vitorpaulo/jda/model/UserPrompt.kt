package dev.vitorpaulo.jda.model

import dev.vitorpaulo.jda.dao.PromptDao
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import java.util.function.Consumer

class UserPrompt(val user: User, val textChannel: TextChannel) {

    val id = "${user.id}:${textChannel.id}"
    var validator: (String) -> Boolean = { true }
    var cancelSequences = arrayListOf<String>()
    var invalidCallback: Consumer<Message>? = null
    var canceledCallback: Consumer<Message>? = null
    var successCallback: Consumer<Message>? = null

    class Builder(user: User, textChannel: TextChannel) {

        private val prompt = UserPrompt(user, textChannel)

        fun validator(validator: (String) -> Boolean) = apply { prompt.validator = validator; return this }

        fun addCancelSequence(string: String) = apply { prompt.cancelSequences.add(string); return this }

        fun cancelSequences(cancelSequences: ArrayList<String>) = apply { prompt.cancelSequences = cancelSequences; return this }

        fun invalidCallback(invalidCallback: Consumer<Message>) = apply { prompt.invalidCallback = invalidCallback; return this }

        fun canceledCallback(canceledCallback: Consumer<Message>) = apply { prompt.canceledCallback = canceledCallback; return this }

        fun successCallback(successCallback: Consumer<Message>) = apply { prompt.successCallback = successCallback; return this }

        fun queue(): UserPrompt {

            PromptDao.add(prompt)
            return prompt

        }

    }

}