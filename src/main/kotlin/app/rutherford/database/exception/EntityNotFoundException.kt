package app.rutherford.database.exception

import org.jooq.Table
import java.util.*

class EntityNotFoundException(table: Table<*>, id: UUID) : RuntimeException("$table with id = $id not found")
