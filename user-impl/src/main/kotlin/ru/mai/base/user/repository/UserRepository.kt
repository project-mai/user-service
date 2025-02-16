/*
package ru.mai.base.user.repository

import org.apache.logging.log4j.kotlin.Logging
import org.jooq.Configuration
import org.springframework.stereotype.Repository
import ru.mai.user.jooq.tables.daos.UserDao
import ru.mai.user.jooq.tables.references.USER

@Repository
class UserRepository(
    val configuration: Configuration
) : UserDao(configuration), Logging {
    fun findUserByName(name: String) = ctx()
        .selectFrom(
            USER
        )
        .where(
            USER.NAME.eq(name)
        )
        .limit(1)
        .fetchLazy()
}*/
