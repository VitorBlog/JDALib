# JDALib
Kotlin Lib to build bots in JDA.

# Download
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
    <groupId>com.github.VitorBlog</groupId>
    <artifactId>JDALib</artifactId>
    <version>0ec2d540de</version>
</dependency>
```

# Example
```kotlin
object Bot {

    @JvmStatic
    fun main(args: Array<String>) {

        JDALib.create("TOKEN").build()

    }

    @CommandInfo(["test"], [])
    class TestCommand : Command() {

        override
        fun execute() {

            reply("test", actionRow = arrayOf(
                ActionRow.of(
                    Button.primary("${user.id}:hello", "Hello?").callback {

                        it.deferEdit().queue()

                        it.message?.editMessage("hello :)")?.queue()

                    }
                )
            ))

        }

    }

}
```