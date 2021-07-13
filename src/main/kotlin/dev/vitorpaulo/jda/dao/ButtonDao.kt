package dev.vitorpaulo.jda.dao

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import java.util.function.Consumer

object ButtonDao {

    private val BUTTONS = hashMapOf<String, Consumer<ButtonClickEvent>>()

    operator fun get(string: String) = BUTTONS[string]

    fun add(string: String, consumer: Consumer<ButtonClickEvent>) = BUTTONS.put(string, consumer)

}