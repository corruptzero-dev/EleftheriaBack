package ru.corruptzero.eleftheriaback.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.corruptzero.eleftheriaback.domain.entity.EOrderStatus.ACTIVE;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    //TODO FOREIGN KEY ADMIN_ID USER_ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime due_to;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ACTIVE'")
    private EOrderStatus status = ACTIVE;

    @OneToOne(mappedBy = "order")
    private User user;

    //TODO foreign key
    @Column(nullable = false)
    private Long admin_id;
}
