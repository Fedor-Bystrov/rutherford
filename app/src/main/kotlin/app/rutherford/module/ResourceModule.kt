package app.rutherford.module

import app.rutherford.auth.resource.AuthResource
import app.rutherford.core.abstract.resource.Resource
import app.rutherford.resource.TestResource
import io.javalin.Javalin

class ResourceModule(
    javalin: Javalin,
    repositoryModule: RepositoryModule,
    managerModule: ManagerModule,
) {
    private val resources: List<Resource>

    init {
        resources = listOf(
            TestResource(javalin, repositoryModule.authUserRepository),
            AuthResource(javalin, managerModule.userManager)
        )
    }

    fun bindRoutes() {
        resources.forEach { it.bindRoutes() }
    }
}
