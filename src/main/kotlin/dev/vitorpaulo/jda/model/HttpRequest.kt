package dev.vitorpaulo.jda.model

import java.net.HttpURLConnection
import java.net.URL
import java.util.function.Consumer

open class HttpRequest(
    val url: String,
    var method: Method = Method.GET,
    val headers: HashMap<String, String> = hashMapOf(),
    var content: Any? = null
) {

    fun setUserAgent(
        string: String = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36"
    ) = addHeader("user-agent", string)

    fun addHeader(key: String, value: Any?): HttpRequest { headers[key] = value.toString(); return this }

    fun setContent(value: Any?): HttpRequest { content = value.toString(); return this }
    
    fun send(success: Consumer<String>? = null, error: Consumer<Exception>? = null) {

        Thread {

            try {

                val connection = URL(url).openConnection() as HttpURLConnection

                connection.requestMethod = method.name

                headers.forEach { connection.addRequestProperty(it.key, it.value) }

                if (content != null) {

                    connection.doOutput = true
                    connection.outputStream.write(content.toString().toByteArray())

                }

                success?.accept(connection.inputStream.reader().readText())

            } catch (exception: Exception) {

                error?.accept(exception)

            }

        }.start()

    }

    enum class Method {

        GET,
        POST,
        HEAD,
        OPTIONS,
        PUT,
        DELETE,
        TRACE;

    }

    /**
     * Accessibility
     */
    fun get(success: Consumer<String>) = get(success, null)
    fun post(success: Consumer<String>) = post(success, null)
    fun send(success: Consumer<String>) = send(success, null)
    fun get(success: Consumer<String>? = null, error: Consumer<Exception>? = null) { method = Method.GET; send(success, error) }
    fun post(success: Consumer<String>? = null, error: Consumer<Exception>? = null) { method = Method.POST; send(success, error) }

}