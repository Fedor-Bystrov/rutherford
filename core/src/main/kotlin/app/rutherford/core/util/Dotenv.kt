package app.rutherford.core.util

import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.dotenv.Dotenv as DotenvKotlin

class DotenvException(key: String) : RuntimeException("No value for $key key")

object Dotenv {
    private val dotenv: DotenvKotlin = dotenv {
        ignoreIfMissing = true
    }

    fun get(key: String): String {
        return try {
            dotenv[key]
        } catch (e: NullPointerException) {
            throw DotenvException(key)
        }
    }

    fun getInt(key: String): Int {
        return try {
            dotenv[key].toInt()
        } catch (e: RuntimeException) {
            when (e) {
                is NullPointerException,
                is NumberFormatException -> throw DotenvException(key)

                else -> throw e
            }
        }
    }
}