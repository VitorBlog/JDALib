package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.Permission

annotation class CommandInfo(val aliases: Array<String>, val permissions: Array<Permission>)