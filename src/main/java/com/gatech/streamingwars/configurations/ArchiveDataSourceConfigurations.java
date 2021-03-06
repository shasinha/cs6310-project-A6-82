package com.gatech.streamingwars.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gatech.streamingwars.archivedb.repository",entityManagerFactoryRef = "archiveEntityManagerFactory",
        transactionManagerRef= "archiveTransactionManager"
)
public class ArchiveDataSourceConfigurations {

    @Bean("archiveDSProperties")
    @ConfigurationProperties("spring.datasource.archive")
    public DataSourceProperties archiveDataSourceProperties()
    {
        return new DataSourceProperties();
    }

    @Bean("archiveDS")
    @ConfigurationProperties("spring.datasource.archive.configuration")
    public DataSource archiveDataSource(@Qualifier("archiveDSProperties") DataSourceProperties archiveDSProperties) {
        return archiveDSProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }

    @Bean(name = "archiveEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory( EntityManagerFactoryBuilder builder, @Qualifier("archiveDS") DataSource archiveDS) {
        return builder.dataSource(archiveDS)
                .packages("com.gatech.streamingwars.archivedb.model")
                .persistenceUnit("archive")
                .build();
    }

    @Bean(name = "archiveTransactionManager")
    public PlatformTransactionManager archiveTransactionManager(
            @Qualifier("archiveEntityManagerFactory") EntityManagerFactory archiveEntityManagerFactory) {
        return new JpaTransactionManager(archiveEntityManagerFactory);
    }
}
