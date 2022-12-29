package ru.corruptzero.eleftheriaback.domain.entity.withdraw;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Entity class representing a withdraw request.
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
@Table(name = "withdraws")
public class Withdraw {
    /**
     * The unique identifier for the withdraw request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Positive
    private Long id;

    /**
     * The current status of the withdraw request.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'PENDING'")
    private EWithdrawStatus status = EWithdrawStatus.PENDING;

    /**
     * The date and time when the withdraw request was created.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();

    /**
     * The amount requested in the withdraw request.
     */
    @Column(nullable = false)
    @Positive    //TODO set minimum value from properties file
    private Integer amount;

    /**
     * Currency of the amount (e.g. "USD", "EUR", etc.)
     */
    @Column(nullable = false)
    @NotBlank
    @Length(min = 1, max = 20)  //review
    private String currency;

    /**
     * The user who made the withdraw request.
     */
    @ManyToOne
    private User user;
}

