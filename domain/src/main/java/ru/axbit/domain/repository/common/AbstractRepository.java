package ru.axbit.domain.repository.common;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.axbit.domain.domain.common.AbstractEntity;

/**
 * Родительский интерфейс изолированного слоя CRUD репозиториев проекта.
 * @param <T> Параметризованный тип, наследуется от {@link AbstractEntity}.
 */
@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Number>,
        JpaSpecificationExecutor<T> {
}
