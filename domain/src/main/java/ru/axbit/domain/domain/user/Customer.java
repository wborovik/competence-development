package ru.axbit.domain.domain.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.axbit.domain.domain.Order;
import ru.axbit.domain.domain.common.UserEntity;

import java.util.List;

@Getter
@Setter
@Entity
public class Customer extends UserEntity {
    private List<Order> orders;
}
