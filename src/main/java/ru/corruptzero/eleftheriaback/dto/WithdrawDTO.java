package ru.corruptzero.eleftheriaback.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Data transfer object for {@link Withdraw} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Getter
@Setter
@Builder
public class WithdrawDTO {

    /**
     * The ID of the withdraw.
     */
    private Long id;

    /**
     * The status of the withdraw.
     */
    private EWithdrawStatus status;

    /**
     * The amount of the withdraw.
     */
    @NotNull
    @Positive    //TODO set minimum value from properties file
    private Integer amount;

    /**
     * Currency of the amount (e.g. "USD", "EUR", etc.)
     */
    @NotNull
    @NotBlank
    private String currency;

    /**
     * The user that requested the withdraw.
     */
    @NotNull
    private User user;

    /**
     * The date and time when the withdraw was created.
     */
    private LocalDateTime created_at;
}

