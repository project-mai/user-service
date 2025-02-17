package ru.mai.base.user.services.kafka.producer

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.mai.base.user.services.kafka.dto.SimpleJsonRequest

@Service
class SimpleProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) : Logging {
    companion object {
        private const val TOPIC_NAME = "simple-topic"
    }

    fun sendSimpleJsonObj(key: String, obj: SimpleJsonRequest): String {
        val completableFut = kafkaTemplate.send(TOPIC_NAME, key, obj)

        completableFut
            .thenAccept { result ->
                logger.info("Message sent to $TOPIC_NAME successfully: $result")
            }
            .exceptionally { throwable ->
                logger.error("Error sending message to $TOPIC_NAME", throwable)
                handleSendSimpleJsonError(obj, throwable)
                null
            }

        return "Message sending initiated for $TOPIC_NAME"
    }

    private fun handleSendSimpleJsonError(message: SimpleJsonRequest, throwable: Throwable) {
        logger.error("Failed to send message: $message", throwable)
    }

}
