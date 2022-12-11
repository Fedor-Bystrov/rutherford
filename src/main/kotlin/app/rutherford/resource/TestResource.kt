package app.rutherford.resource

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path

class TestResource(private val javalin: Javalin) : Resource {
    override fun bindRoutes() {
        javalin.routes {
            path("/test") {
                get { ctx -> ctx.result("Hello World") }
            }
        }
    }
}