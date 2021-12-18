# JDALib
Kotlin Lib to build bots in JDA.

# Download
Repository:
```xml
<repository>
    <id>VitorPaulo</id>
    <name>Vitor Paulo Repository</name>
    <url>https://repository.vitorpaulo.dev/releases</url>
</repository>
```
Dependency:
```xml
<dependency>
    <groupId>dev.vitorpaulo</groupId>
    <artifactId>JDALib</artifactId>
    <version>1.3.0</version>
</dependency>
```

# Examples
```kotlin
fun main(args: Array<String>) {

    JDALib.create("TOKEN").build()

}
```

### Command
```kotlin
@CommandInfo(["test"], [])
class TestCommand : Command() {

    override
    fun execute() {

        reply("test")

    }

}
```

### Reply
```kotlin
reply(
    content = "Hello", // Message content, can be String or MessageEmbed and it's required.
    file = File("test.png"), // Attachment to be sent, optional and doesn't works with SlashCommands.
    replaceMessage = null, // Message that will be edited, optional and doesn't works with SlashCommands.
    actionRow = arrayOf(), // List of Actions (Buttons) to be sent, optional.
    success = Consumer {},  // Called when the message is sent, optional and doesn't works with SlashCommands.
    error = Consumer {}  // If you get some error while sending a message you can get it there, optional.
)
```

### Button
```kotlin
reply("test", actionRow = arrayOf(
    ActionRow.of(
        Button.primary("${user.id}:hello", "Hello?").callback {
            buttonEvent ->

                buttonEvent.deferEdit().queue()
                buttonEvent.message?.delete()?.queue()

                reply("Hello :)")

        }
    )
))
```

### Prompt
```kotlin
UserPrompt.Builder(user, channel)
    .validator {
        !it.contentRaw.startsWith("Gabriel")
    }
    .invalidCallback {
        reply("That's an invalid name.")
    }
    .addCancelSequence("cancel")
    .canceledCallback {
        reply("Bye!")
    }
    .successCallback {
        reply("Hello, ${it.contentRaw.safeString()}!")
    }
    .queue()
```

### SlashCommand
```kotlin
/**
 * Slash commands still very limited in JDA/Discord.
 * Please read more about it at: https://gist.github.com/MinnDevelopment/b883b078fdb69d0e568249cc8bf37fe9
 *
 * If you want just a slash command you can remove all the aliases and it will be just it.
 */
@CommandInfo(["slashtest"], [])
class SlashTestCommand: Command(
    CommandData("slashtest", "What's your name")
        .addOption(OptionType.STRING, "name", "Your name", true)
) {

    override
    fun execute() {

        if (arguments.isEmpty()) {

            reply("Can't find your name.")
            return

        }

        reply("Your name is: `${arguments[0].safeString()}`")

    }

}
```

### Open Private Channel
```kotlin
reply("Hello guild!")

openPrivateChannel()

reply("Hello DM!")
```
