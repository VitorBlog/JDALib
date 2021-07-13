package dev.vitorpaulo.jda.process

import dev.vitorpaulo.jda.dao.CommandDao
import dev.vitorpaulo.jda.model.Command
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner

object CommandProcess {

    fun load() {

        val reflections = Reflections(SubTypesScanner())

        val commands = reflections.getSubTypesOf(Command::class.java)
        commands.forEach { CommandDao.add(it.newInstance()) }

    }

}