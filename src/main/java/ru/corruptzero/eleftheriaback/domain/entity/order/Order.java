package ru.corruptzero.eleftheriaback.domain.entity.order;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ru.corruptzero.eleftheriaback.domain.entity.order.EOrderStatus.ACTIVE;

/**
 * Entity class representing an order.
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
@Table(name = "orders")
public class Order {
    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The title of the order.
     */
    @Column(nullable = false)
    @NotBlank
    private String title;

    /**
     * The description of the order.
     */
    @Column(nullable = false)
    @NotBlank
    private String description;

    /**
     * The skills required for the order.
     */
    @Builder.Default
    @ElementCollection
    @Size(min = 1, max = 10)
    @Column(nullable = false)
    private Set<String> skills = new HashSet<>();

    /**
     * The value of the order.
     */
    @Column(nullable = false)
    @Positive    //TODO set minimum value from properties file
    private Integer value;

    /**
     * The date and time when the order was created.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();

    /**
     * The date and time when the order is due.
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime due_to = LocalDateTime.now();

    /**
     * The current status of the order.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ACTIVE'")
    private EOrderStatus status = ACTIVE;

    /**
     * The user who took the order.
     */
    @OneToOne(mappedBy = "order")
    private User user;

    /**
     * The administrator assigned to the order.
     */
    @OneToOne()
    @JoinTable(name = "order_admin",
            joinColumns =
                    {@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns =
                    {@JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)})
    private User admin;
}
