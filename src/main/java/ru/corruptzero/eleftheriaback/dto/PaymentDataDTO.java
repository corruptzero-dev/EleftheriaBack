package ru.corruptzero.eleftheriaback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

/**
 * Data transfer object for {@link PaymentData} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Getter
@Setter
@Builder
public class PaymentDataDTO {

    /**
     * The ID of the payment data.
     */
    private Long id;

    /**
     * Payment method (e.g. "PayPal", "Bank Transfer", etc.)
     */
    private String paymentMethod;

    /**
     * Account number of the recipient (e.g. the user's PayPal email address, bank account number)
     */
    private String accountNumber;

    /**
     * Name of the bank where the recipient has their account (only applicable for bank transfers)
     */
    private String bankName;

    /**
     * The user who added their payment data.
     */
    private User user;

}
