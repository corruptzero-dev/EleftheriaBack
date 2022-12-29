package ru.corruptzero.eleftheriaback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.dto.OrderDTO;

/**
 * Mapper that maps {@link Order} entities to and from {@link OrderDTO} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Maps the given {@link Order} entity to an {@link OrderDTO} object.
     *
     * @param order the {@link Order} entity to map
     * @return the resulting {@link OrderDTO} object
     */
    OrderDTO toDTO(Order order);

    /**
     * Maps the given {@link OrderDTO} object to an {@link Order} entity.
     *
     * @param orderDTO the {@link OrderDTO} object to map
     * @return the resulting {@link Order} entity
     */
    Order toEntity(OrderDTO orderDTO);
}

