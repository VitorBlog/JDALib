package dev.vitorpaulo.jda.dao

import dev.vitorpaulo.jda.model.UserPrompt

object PromptDao {

	private val PROMPTS = hashMapOf<String, UserPrompt>()

	operator fun get(id: String) = PROMPTS[id]

	fun add(userPrompt: UserPrompt) = PROMPTS.put(userPrompt.id, userPrompt)

	fun remove(id: String) = PROMPTS.remove(id)

}