package ru.mai.base.user.services.kafka.consumer

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Service
import ru.mai.base.user.services.kafka.dto.SimpleJsonRequest

@Service
@RetryableTopic(
    attempts = "3",
    backoff = Backoff(delay = 1000), // Задержка в мс перед повторными попытками
    dltStrategy = DltStrategy.FAIL_ON_ERROR
)
@KafkaListener(
    topics = ["simple-topic"],
    groupId = "sample-group-id"
)
class SimpleKafkaConsumer : Logging {


    @KafkaHandler
    fun consume(
        message: SimpleJsonRequest,
        @Header(KafkaHeaders.RECEIVED_KEY) key: String,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) timestamp: Long
    ) {
        logger.debug() {
            """
            |Received message:
            |  Message    : $message
            |  Partition  : $partition
            |  Offset     : $offset
            |  Timestamp  : $timestamp
            |  Key        : $key
            """.trimMargin()
        }
        logger.info("Consumer received: $key $message")
    }

    @KafkaHandler(isDefault = true)
    fun consumeFallback(
        data: Any,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) timestamp: Long,
        @Header(KafkaHeaders.KEY, required = false) key: String?
    ) {
        logger.error {
            """
            |Received unknown or invalid data:
            |  Data       : $data
            |  Partition  : $partition
            |  Offset     : $offset
            |  Timestamp  : $timestamp
            |  Key        : $key
            """.trimMargin()
        }
    }
}
