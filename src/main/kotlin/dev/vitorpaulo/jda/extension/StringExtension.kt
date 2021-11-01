package dev.vitorpaulo.jda.extension

object StringExtension {

	fun String.safeString() = this.replace("`", "'").replace("@everyone", "(a)everyone").replace("@here", "(a)here")

	fun String.substringBigger(start: Int, end: Int = 0): String {
		return if (this.length >= end) this.substring(start, end) else this
	}

}