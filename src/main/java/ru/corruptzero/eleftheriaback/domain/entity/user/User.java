package ru.corruptzero.eleftheriaback.domain.entity.user;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ru.corruptzero.eleftheriaback.domain.entity.user.EUserRole.USER;

/**
 * Entity class representing a user.
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
@Table(name = "users")
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The username of the user.
     */
    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    /**
     * The email address of the user.
     */
    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;

    /**
     * The password of the user.
     */
    @Column(nullable = false)
    @NotBlank
    private String password;

    /**
     * The balance of the user.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "integer default 0")
    @Min(0)
    private Integer balance = 0;

    /**
     * The withdraw request made by the user.
     */
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_withdraw",
            joinColumns = {@JoinColumn(name = "user_id",
                    referencedColumnName = "id",
                    nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "withdraw_id",
                    referencedColumnName = "id",
                    nullable = false)})
    private Set<Withdraw> withdraws = new HashSet<>();

    /**
     * The payment data of the user.
     */
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_paymentdata",
            joinColumns = {@JoinColumn(name = "user_id",
                    referencedColumnName = "id",
                    nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "paymentdata_id",
                    referencedColumnName = "id",
                    nullable = false)})
    private Set<PaymentData> paymentData = new HashSet<>();

    /**
     * The role of the user.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "varchar(255) default 'USER'")
    @Enumerated(EnumType.STRING)
    private EUserRole role = USER;

    /**
     * The order which is currently in completion by the user.
     */
    @OneToOne()
    @JoinTable(name = "user_order",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns =
                    {@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)})
    private Order order;

    /**
     * The date and time when the user was created.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();
}
