package dev.vitorpaulo.jda.handler

import dev.vitorpaulo.jda.dao.ButtonDao
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ButtonHandler : ListenerAdapter() {

    override
    fun onButtonClick(event: ButtonClickEvent) {

        val split = (event.button?.id ?: "").split(":")

        if (event.user.isBot || (event.user.id != split[0] && (event.button?.id ?: "").contains(":"))) return

        ButtonDao[event.button?.id ?: ""]?.accept(event)

    }

}