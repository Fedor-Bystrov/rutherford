package app.rutherford.module.tool

import app.rutherford.module.exception.DotenvException
import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.dotenv.Dotenv as DotenvKotlin

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