package app.rutherford.module.tool

import app.rutherford.module.exception.DotenvException
import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.dotenv.Dotenv as DotenvKotlin

object Dotenv { // TODO add test
    private val dotenv: DotenvKotlin = dotenv()

    fun get(key: String): String {
        return dotenv.get(key) ?: throw DotenvException(key)
    }

    fun getInt(key: String): Int {
        return dotenv.get(key).toIntOrNull() ?: throw DotenvException(key)
    }
}