package com.invstore.invstorejvm

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.test.context.TestPropertySource
import java.util.function.Supplier
import javax.sql.DataSource


@Configuration
@Profile("Test")
class DatabaseConfig {
    var logger: Logger = LoggerFactory.getLogger(DatabaseConfig::class.java)

    @Value("\${spring.datasource.url}")
    private val dbUrl: String? = null

    @Bean
    fun dataSource(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = dbUrl
        dataSource.driverClassName ="org.h2.Driver"
        dataSource.username = "sa"
        dataSource.password = "sa"

        logger.info { "Database Initialized" }
        return dataSource
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource?,
        jpaVendorAdapter: JpaVendorAdapter?
    ): LocalContainerEntityManagerFactoryBean {
        val emfb = LocalContainerEntityManagerFactoryBean()
        emfb.dataSource = dataSource
        emfb.jpaVendorAdapter = jpaVendorAdapter
        emfb.setPackagesToScan("com.invstore.invstorejvm.models") // replace with your package where entity classes are
        return emfb
    }

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        val jpaVendorAdapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter.setDatabase(Database.H2)
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect")
        jpaVendorAdapter.setGenerateDdl(true)
        return jpaVendorAdapter
    }

    @Bean
    fun transactionManager(emf: EntityManagerFactory?): JpaTransactionManager {
        return JpaTransactionManager(emf!!)
    }
}