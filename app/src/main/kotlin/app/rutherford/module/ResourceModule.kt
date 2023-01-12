package app.rutherford.module

import app.rutherford.auth.resource.AuthResource
import app.rutherford.core.abstract.resource.Resource
import io.javalin.Javalin

class ResourceModule(
    javalin: Javalin,
    managerModule: ManagerModule,
) {
    private val resources: List<Resource>

    init {
        resources = listOf(
            AuthResource(javalin, managerModule.userManager)
        )
    }

    fun bindRoutes() {
        resources.forEach { it.bindRoutes() }
    }
}
