package app.rutherford.core.exception

import app.rutherford.core.ErrorCode
import app.rutherford.core.ErrorCode.ENTITY_NOT_FOUND
import org.jooq.Table
import java.util.UUID

class EntityNotFoundException(table: Table<*>, id: UUID) : RutherfordException("${table.name} with id: $id not found") {
    override fun errorCode(): ErrorCode = ENTITY_NOT_FOUND
}
