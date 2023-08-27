package ru.axbit.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Основной класс для запуска приложения.
 */
@EnableJpaAuditing
@SpringBootApplication
@EnableTransactionManagement
@EntityScan(value = {"ru.axbit.domain"})
@EnableJpaRepositories(value = {"ru.axbit.domain"})
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
