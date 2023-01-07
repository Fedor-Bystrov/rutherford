package app.rutherford.resource

import io.grpc.examples.helloworld.GreeterGrpcKt
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest

class GreeterService : GreeterGrpcKt.GreeterCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloReply = HelloReply.newBuilder()
        .setMessage("Hello ${request.name}")
        .build()

    override suspend fun sayHelloAgain(request: HelloRequest): HelloReply = HelloReply.newBuilder()
        .setMessage("Hello Again ${request.name}")
        .build()
}
