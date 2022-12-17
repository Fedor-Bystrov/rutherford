package app.rutherford.module.exception

class DotenvException(key: String) : RuntimeException("No value for $key key");