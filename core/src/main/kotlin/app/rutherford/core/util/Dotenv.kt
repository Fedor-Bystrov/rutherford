package app.rutherford.core.util

import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.dotenv.Dotenv as DotenvKotlin

class DotenvException(key: String) : RuntimeException("No value for $key key")

object Dotenv {
    private val dotenv: DotenvKotlin = dotenv()

    fun get(key: String): String {
        return try {
            dotenv.get(key)
        } catch (e: NullPointerException) {
            throw DotenvException(key)
        }
    }

    fun getInt(key: String): Int {
        return try {
            dotenv.get(key).toInt()
        } catch (e: RuntimeException) {
            when (e) {
                is NullPointerException,
                is NumberFormatException -> throw DotenvException(key)

                else -> throw e
            }
        }
    }
}