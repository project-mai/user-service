package ru.mai.base.user.services.kafka.producer

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.mai.base.user.services.kafka.dto.SimpleJsonRequest
import java.time.Instant
import java.util.*

@Component
class ScheduleProducer(
    private val producer: SimpleProducerService
) {
    @Scheduled(fixedRate = 5000)
    fun sendPeriodicMessage() {
        val message = SimpleJsonRequest(
            name = "MyApplication",
            id = Instant.now().epochSecond
        )
        producer.sendSimpleJsonObj(UUID.randomUUID().toString(), message)
    }
}