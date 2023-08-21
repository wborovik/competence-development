package ru.axbit.service.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.axbit.domain.domain.common.AbstractEntity;
import ru.axbit.domain.domain.order.Order;
import ru.axbit.domain.repository.OrderRepository;
import ru.axbit.service.service.OrderService;
import ru.axbit.service.service.soap.mapper.request.CommonMapperDTO;
import ru.axbit.service.service.soap.mapper.response.OrderListPojo;
import ru.axbit.service.service.soap.mapper.response.ResponseMapper;
import ru.axbit.service.service.soap.spec.OrderSpecification;
import ru.axbit.service.util.PagingUtils;
import ru.axbit.vborovik.competence.core.v1.PagingOptions;
import ru.axbit.vborovik.competence.filtertypes.v1.GetOrderListFilterType;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListRequest;
import ru.axbit.vborovik.competence.userservice.types.v1.GetOrderListResponse;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public GetOrderListResponse getOrderList(GetOrderListRequest body) {
        var orderPojo = getOrderList(body.getFilter(), body.getPagingOptions());

        return ResponseMapper.mapGetOrderResponse(orderPojo);
    }

    private OrderListPojo getOrderList(GetOrderListFilterType filter, PagingOptions pagingOptions) {
        if (Objects.isNull(filter)) return null;
        var pageRequest = PagingUtils.getPageRequest(pagingOptions);
        var sorting = PagingUtils.getSortOptions(pagingOptions, AbstractEntity.Fields.id);
        var criteriaDTO = CommonMapperDTO.mapOrderDTO(filter);
        Specification<Order> specification = OrderSpecification.create(criteriaDTO, sorting);
        Page<Order> orders = orderRepository.findAll(specification, pageRequest);

        return OrderListPojo.builder()
                .orders(orders)
                .build();
    }
}
