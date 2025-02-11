package ru.mai.user.common.config.database

import liquibase.integration.spring.SpringLiquibase
import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionProvider
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource
import org.jooq.Configuration as ConfigurationJooq

@Configuration
class PrimaryDatabaseConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.primary")
    fun primaryDataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary.hikari")
    fun primaryDataSource(): DataSource = primaryDataSourceProperties().initializeDataSourceBuilder().build()

    @Bean("primaryTransactionManager")
    @Primary
    fun primaryTransactionManager(): DataSourceTransactionManager =
        DataSourceTransactionManager(primaryDataSource())

    @Bean("primaryConnectionProvider")
    fun primaryConnectionProvider(): ConnectionProvider =
        DataSourceConnectionProvider(TransactionAwareDataSourceProxy(primaryDataSource()))

    @Bean("primaryTransactionProvider")
    fun primaryTransactionProvider(): TransactionProvider = SpringTransactionProvider(primaryTransactionManager())

    @Bean("primaryTransactionTemplate")
    fun primaryTransactionTemplate(): TransactionTemplate = TransactionTemplate(primaryTransactionManager())

    @Bean("primaryDbConfiguration")
    fun primaryJooqConfiguration(): ConfigurationJooq {
        val defaultConfiguration =
            DefaultConfiguration()
                .derive(primaryConnectionProvider())
                .derive(primaryTransactionProvider())
                .derive(SQLDialect.POSTGRES)
        defaultConfiguration.settings().isReturnRecordToPojo = false
        defaultConfiguration.settings().isReturnAllOnUpdatableRecord = false
        defaultConfiguration.settings().batchSize = 500
        return defaultConfiguration
    }

    @Bean("primaryDslContext")
    @Primary
    fun primaryDslContext(): DSLContext = DefaultDSLContext(primaryJooqConfiguration())

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary.liquibase")
    fun primaryLiquibaseProperties(): LiquibaseProperties = LiquibaseProperties()

    @Bean("primaryLiquibase")
    fun primaryLiquibase(
        @Qualifier("primaryDataSource") primaryDataSource: DataSource,
    ): SpringLiquibase = springLiquibase(primaryDataSource, primaryLiquibaseProperties())

    private fun springLiquibase(
        dataSource: DataSource,
        properties: LiquibaseProperties,
    ): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = dataSource
        liquibase.changeLog = properties.changeLog
        liquibase.defaultSchema = properties.defaultSchema
        liquibase.isDropFirst = properties.isDropFirst
        liquibase.liquibaseSchema = properties.liquibaseSchema
        liquibase.setShouldRun(properties.isEnabled)
        liquibase.setChangeLogParameters(properties.parameters)
        liquibase.setRollbackFile(properties.rollbackFile)
        return liquibase
    }
}
