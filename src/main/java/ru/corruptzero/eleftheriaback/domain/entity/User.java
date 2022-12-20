package ru.corruptzero.eleftheriaback.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.corruptzero.eleftheriaback.domain.entity.ERole.USER;

@Entity
@Builder
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer balance = 0;

    @OneToOne()
    @JoinTable(name = "user_withdraw",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns =
                    {@JoinColumn(name = "withdraw_id", referencedColumnName = "id", nullable = false)})
    private Withdraw withdraw;

    @Column
    private String paymentData;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "varchar(255) default 'USER'")
    @Enumerated(EnumType.STRING)
    private ERole role = USER;

    @OneToOne()
    @JoinTable(name = "user_order",
            joinColumns =
                    {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns =
                    {@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)})
    private Order order;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();


}
