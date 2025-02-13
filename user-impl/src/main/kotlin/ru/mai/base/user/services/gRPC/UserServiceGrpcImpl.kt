package ru.mai.base.user.services.gRPC

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.mai.base.user.repository.UserRepository
import ru.mai.user.proto.UserServiceGrpc

@Service
class UserServiceGrpcImpl(
    private val userRepository: UserRepository
) {
    @GrpcClient("analytic", interceptors = [])
    private lateinit var client: UserServiceGrpc.UserServiceBlockingStub

    fun demo() {
        userRepository.findUserByName("puk").use {
            it.forEach {
                println(it.name)
            }
        }
    }
}