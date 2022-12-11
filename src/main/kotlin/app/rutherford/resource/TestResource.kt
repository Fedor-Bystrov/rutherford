//package app.rutherford.resource
//
//import app.rutherford.database.repository.AuthUserRepository
//import io.javalin.Javalin
//import io.javalin.apibuilder.ApiBuilder.get
//import io.javalin.apibuilder.ApiBuilder.path
//import io.javalin.http.Context
//
//class TestResource(
//    private val javalin: Javalin,
//    private val authUserRepository: AuthUserRepository
//) : Resource {
//    override fun bindRoutes() {
//        javalin.routes {
//            path("/test") {
//                get(allUsers())
//            }
//        }
//    }
//
//    private fun allUsers(): (Context) -> Unit {
//        return { context ->
//            val users = authUserRepository.findAll()
//            context.json(users)
//        }
//    }
//}