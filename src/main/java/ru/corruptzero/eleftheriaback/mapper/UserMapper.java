package ru.corruptzero.eleftheriaback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.dto.UserDTO;

/**
 * Mapper that maps {@link User} entities to and from {@link UserDTO} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a {@link User} entity to its corresponding data transfer object ({@link UserDTO}).
     *
     * @param user the user entity to convert
     * @return the user data transfer object
     */
    UserDTO toDTO(User user);

    /**
     * Converts a {@link UserDTO} to its corresponding {@link User} entity.
     *
     * @param userDTO the user data transfer object to convert
     * @return the user entity
     */
    User toEntity(UserDTO userDTO);
}
