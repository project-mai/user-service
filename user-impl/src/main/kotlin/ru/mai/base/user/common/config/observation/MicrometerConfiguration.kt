package ru.mai.user.common.config.observation

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Metrics
import io.micrometer.core.instrument.Tag
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Configuration
class MetersConfig {
    @Bean
    fun meterRegistry() {

    }

    @Bean
    fun micrometerMeters(meterRegistry: MeterRegistry): Meters = Meters(meterRegistry)
}

/**
 * Набор методов для создания счётчиков и таймеров
 */
class Meters(
    registry: MeterRegistry,
) : Logging {
    init {
        meterRegistry = registry
    }

    companion object : Logging {
        @JvmStatic
        private lateinit var meterRegistry: MeterRegistry

        private val gaugesMap = ConcurrentHashMap<String, AtomicInteger>()

        fun setGauge(
            name: String,
            value: Int,
            vararg tags: Tag,
        ) {
            val tagsList = listOf(*tags)
            val key = buildKey(name, tagsList)
            gaugesMap.computeIfAbsent(key) {
                Metrics.gauge(name, tagsList, AtomicInteger())
                    ?: throw RuntimeException("Could not initialize $name")
            }
            gaugesMap[key]!!.set(value)
        }

        private fun buildKey(
            name: String,
            tags: List<Tag>,
        ): String {
            val tagsKey = tags.joinToString(",")
            return "$name:$tagsKey"
        }
    }

    enum class MetricName(
        val literal: String,
    ) {
        SAMPLE_METRIC("sample_metric")
    }

    enum class MeterTag(
        val literal: String,
    ) {
        STATE("state"),
        APPLICATION_ID("application_id"),
    }

    enum class MeterState(
        val literal: String,
    ) {
        SUCCESS("success"),
        FAIL("fail"),
    }
}
