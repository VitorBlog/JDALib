package dev.vitorpaulo.jda.extension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.vitorpaulo.jda.JDALib

object StringExtension {

	fun String.safeString() = this.replace("`", "'").replace("@everyone", "(a)everyone").replace("@here", "(a)here")

	fun String.substringBigger(start: Int, end: Int = 0): String {
		return if (this.length >= end) this.substring(start, end) else this
	}

	fun String.toJson() = this.toJson(JsonObject::class.java)

	fun <T> String.toJson(clazz: Class<T>): T = JDALib.gson.fromJson(this, clazz)

	fun String.toJsonArray() = this.toJson(JsonArray::class.java)

}