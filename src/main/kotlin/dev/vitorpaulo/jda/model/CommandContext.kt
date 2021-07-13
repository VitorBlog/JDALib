package dev.vitorpaulo.jda.model

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member

class CommandContext(info: CommandInfo, val clazz: Command) {

    val aliases: Array<String> = info.aliases
    val permissions: Array<Permission> = info.permissions

    fun checkAliases(string: String) = aliases.contains(string)
    fun checkPermissions(member: Member) = member.hasPermission(permissions.toMutableList())

}