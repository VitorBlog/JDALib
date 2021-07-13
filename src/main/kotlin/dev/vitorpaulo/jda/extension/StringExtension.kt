package dev.vitorpaulo.jda.extension

import dev.vitorpaulo.jda.dao.ButtonDao
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.interactions.components.Button
import java.util.function.Consumer

object StringExtension {

    fun String.safeString() = this.replace("`", "'").replace("@everyone", "(a)everyone").replace("@here", "(a)here")

}