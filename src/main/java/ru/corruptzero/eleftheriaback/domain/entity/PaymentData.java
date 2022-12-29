package ru.corruptzero.eleftheriaback.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Entity class representing the payment data for a user.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */

@Entity
@Builder
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "payment_data")
public class PaymentData {
    /**
     * The unique identifier for the payment data.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Payment method (e.g. "PayPal", "Bank Transfer", etc.)
     */
    @Column(nullable = false)
    @NotBlank
    private String paymentMethod;

    /**
     * Account number of the recipient (e.g. the user's PayPal email address, bank account number)
     */
    @Column(nullable = false)
    @NotBlank
    private String accountNumber;

    /**
     * Name of the bank where the recipient has their account (only applicable for bank transfers)
     */
    @Column
    private String bankName;

    /**
     * The user who added their payment data.
     */
    @ManyToOne
    private User user;

}
