package ru.axbit.domain.repository;

import ru.axbit.domain.domain.user.Customer;
import ru.axbit.domain.repository.common.AbstractRepository;

import java.util.Set;

public interface CustomerRepository extends AbstractRepository<Customer> {

    Set<Customer> findAllByIdInAndDeletedIsFalse(Set<Long> ids);
}
