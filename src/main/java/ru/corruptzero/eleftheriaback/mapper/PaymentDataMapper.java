package ru.corruptzero.eleftheriaback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.dto.PaymentDataDTO;

/**
 * Mapper that maps {@link PaymentData} entities to and from {@link PaymentDataDTO} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Mapper(componentModel = "spring")
public interface PaymentDataMapper {
    PaymentDataMapper INSTANCE = Mappers.getMapper(PaymentDataMapper.class);

    /**
     * Converts a {@link PaymentData} entity to its corresponding data transfer object ({@link PaymentDataDTO}).
     *
     * @param paymentData the user entity to convert
     * @return the payment data transfer object
     */
    PaymentDataDTO toDTO(PaymentData paymentData);

    PaymentData toEntity(PaymentDataDTO paymentDataDTO);
}
