package ru.corruptzero.eleftheriaback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.EUserRole;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object for {@link User} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Getter
@Setter
@Builder
public class UserDTO {

    /**
     * The ID of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The role of the user.
     */
    private EUserRole role;

    /**
     * The balance of the user.
     */
    @Min(0)
    private Integer balance;

    /**
     * The payment data of the user.
     */
    private Set<PaymentData> paymentData;

    /**
     * Withdraws associated with the user.
     */
    private Set<Withdraw> withdraws;

    /**
     * The order associated with the user.
     */

    private Order order;

    /**
     * The date and time when the user was created.
     */
    private LocalDateTime created_at;

}

