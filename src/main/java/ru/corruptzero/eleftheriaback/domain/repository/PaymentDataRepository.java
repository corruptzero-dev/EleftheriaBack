package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;

/**
 * Repository interface for {@link PaymentData} entities.
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {
}
