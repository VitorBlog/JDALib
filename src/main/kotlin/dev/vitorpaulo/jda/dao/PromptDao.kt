package dev.vitorpaulo.jda.dao

import dev.vitorpaulo.jda.model.EmoteAction
import dev.vitorpaulo.jda.model.UserPrompt
import net.dv8tion.jda.api.entities.Emoji

object PromptDao {

    private val PROMPTS = hashMapOf<String, UserPrompt>()

    operator fun get(id: String) = PROMPTS[id]

    fun add(userPrompt: UserPrompt) = PROMPTS.put(userPrompt.id, userPrompt)

    fun remove(id: String) = PROMPTS.remove(id)

}