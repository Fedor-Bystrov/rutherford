package app.rutherford.core

enum class ErrorCode {
    INTERNAL_SERVER_ERROR,
    MALFORMED_JSON,
    VALIDATION_ERROR,
    ENTITY_NOT_FOUND,
    APPLICATION_NOT_FOUND,
    USER_ALREADY_EXIST,
    PASSWORD_POLICY_VIOLATION
}
