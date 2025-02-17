package ru.mai.base.user.common.config.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String,

    @Value("\${spring.kafka.consumer.group-id:default-group-id}")
    private val groupId: String,

    @Value("\${spring.kafka.listener.concurrency:3}")
    private val concurrency: Int,

    @Value("\${spring.kafka.consumer.auto-offset-reset:earliest}")
    private val autoOffsetReset: String,

    private val kafkaProperties: KafkaProperties
) : Logging {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val props = kafkaProperties.buildConsumerProperties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = autoOffsetReset
        return DefaultKafkaConsumerFactory(props)
    }

    /**
     * Настройка конкурентных консьюмеров для использования @KafkaListener
     */
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory()
        factory.setConcurrency(concurrency)
        return factory
    }
}
