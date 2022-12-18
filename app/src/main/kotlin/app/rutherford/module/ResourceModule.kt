package app.rutherford.module

import app.rutherford.core.resource.Resource
import app.rutherford.resource.TestResource
import io.javalin.Javalin

class ResourceModule(javalin: Javalin, repositoryModule: RepositoryModule) {
    private val resources: List<Resource>

    init {
        resources = listOf(
            TestResource(javalin, repositoryModule.authUserRepository)
        )
    }

    fun bindRoutes() {
        resources.forEach { it.bindRoutes() }
    }
}