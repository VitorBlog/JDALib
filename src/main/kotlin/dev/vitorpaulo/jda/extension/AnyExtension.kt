package dev.vitorpaulo.jda.extension

import dev.vitorpaulo.jda.JDALib

object AnyExtension {

	fun Any.toJson() = JDALib.gson.toJson(this)

}