package ru.corruptzero.eleftheriaback.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "withdraws")
@NoArgsConstructor
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'PENDING'")
    private EWithdrawStatus status;

    @Column(nullable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false)
    private Integer amount;

    @OneToOne(mappedBy = "withdraw")
    private User user;
}
