package ru.corruptzero.eleftheriaback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.corruptzero.eleftheriaback.domain.entity.order.EOrderStatus;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object for {@link Order} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Getter
@Setter
@Builder
public class OrderDTO {

    /**
     * The ID of the order.
     */
    private Long id;

    /**
     * The title of the order.
     */
    private String title;

    /**
     * The description of the order.
     */
    private String description;

    /**
     * The required skills for the order.
     */
    @Size(max = 10)
    private Set<String> skills;

    /**
     * The value of the order.
     */
    @Positive    //TODO set minimum value from properties file
    private Integer value;

    /**
     * The due date for the order.
     */
    private LocalDateTime due_to;

    /**
     * The current status of the order.
     */
    private EOrderStatus status;

    /**
     * The user who took the order.
     */
    private User user;

    /**
     * The admin who is handling the order.
     */
    private User admin;

    /**
     * The date and time when the order was created.
     */
    private LocalDateTime created_at;
}




