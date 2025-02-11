package ru.mai.user.utils

import com.google.protobuf.Timestamp
import com.google.type.Decimal
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId

fun toProtobufTimestamp(localDateTime: LocalDateTime): Timestamp =
    Timestamp
        .newBuilder()
        .apply {
            val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
            this.nanos = instant.nano
            this.seconds = instant.epochSecond
        }.build()

fun toProtobufDecimal(bigDecimal: BigDecimal): Decimal =
    Decimal
        .newBuilder()
        .apply {
            this.value = bigDecimal.toString()
        }.build()
