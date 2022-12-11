package app.rutherford.module

import app.rutherford.resource.Resource
import app.rutherford.resource.TestResource
import io.javalin.Javalin

class ResourceModule(javalin: Javalin) {
    private val resources: List<Resource>

    init {
        resources = listOf(
            TestResource(javalin)
        )
    }

    fun bindRoutes() {
        resources.forEach { it.bindRoutes() }
    }
}