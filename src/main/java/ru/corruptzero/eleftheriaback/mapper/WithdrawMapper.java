package ru.corruptzero.eleftheriaback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.dto.WithdrawDTO;

/**
 * Mapper that maps {@link Withdraw} entity to and from {@link WithdrawDTO} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzeroo
 */
@Mapper(componentModel = "spring")
public interface WithdrawMapper {

    WithdrawMapper INSTANCE = Mappers.getMapper(WithdrawMapper.class);

    /**
     * Maps a {@link Withdraw} entity to a {@link WithdrawDTO}.
     *
     * @param withdraw the entity to map
     * @return the corresponding DTO
     */
    WithdrawDTO toDTO(Withdraw withdraw);

    /**
     * Maps a {@link WithdrawDTO} to a {@link Withdraw} entity.
     *
     * @param withdrawDTO the DTO to map
     * @return the corresponding entity
     */
    Withdraw toEntity(WithdrawDTO withdrawDTO);
}
