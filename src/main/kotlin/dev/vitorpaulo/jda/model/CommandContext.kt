package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class CommandContext(info: CommandInfo, val clazz: Command) {

    val aliases: Array<String> = info.aliases
    val permissions: Array<Permission> = info.permissions
    val slashCommand: CommandData? = clazz.slashCommand

    fun checkAliases(string: String) = aliases.any { it.equals(string, true) }
    fun checkPermissions(member: Member) = member.hasPermission(permissions.toList())

}