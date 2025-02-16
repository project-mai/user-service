package ru.mai.base.user.services.gRPC

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.mai.user.proto.UserServiceGrpc

@Service
class UserServiceGrpcImpl(
) {
    @GrpcClient("analytic", interceptors = [])
    private lateinit var client: UserServiceGrpc.UserServiceBlockingStub

    fun demo() {
    }
}