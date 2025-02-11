package ru.mai.base.user.presentation.gRPC

import io.grpc.stub.StreamObserver
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.grpc.server.service.GrpcService
import ru.mai.user.proto.User
import ru.mai.user.proto.UserServiceGrpc

@GrpcService
class UserServiceGrpc : UserServiceGrpc.UserServiceImplBase(), Logging {
    override fun createUser(
        request: User.CreateUserRequest?,
        responseObserver: StreamObserver<User.CreateUserResponse>?
    ) {
        logger.info { "Create user request: $request" }
    }

    override fun getUserData(
        request: User.GetUserDataRequest?,
        responseObserver: StreamObserver<User.GetUserDataResponse>?
    ) {
        logger.info { "Get user data request: $request" }
    }
}