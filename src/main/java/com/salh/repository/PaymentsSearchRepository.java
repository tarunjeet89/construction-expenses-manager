package com.salh.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.salh.dto.PaymentResponseExtendedDTO;

@Repository
public class PaymentsSearchRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Page<PaymentResponseExtendedDTO> getPaymentsByCriteria(String workerName, Date paymentDateFrom, Date paymentDateTo, 
          Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(
                """
                SELECT new com.salh.dto.PaymentResponseExtendedDTO(
                    p.id,
                    p.constructionMember.id,
                    p.amount,
                    p.paymentStatus,
                    p.paymentMode,
                    p.paymentDate,
                    p.paymentIntent,
                    pd.id,
                    p.constructionMember.name
                )
                FROM Payments p
                LEFT JOIN PaymentDocuments pd ON p = pd.payments
                WHERE 1=1
                """
            );

        StringBuilder countQueryBuilder = new StringBuilder(
                """
                SELECT COUNT(p.id)
                FROM Payments p
                LEFT JOIN PaymentDocuments pd ON p = pd.payments
                WHERE 1=1
                """
        );

        Map<String, Object> parameters = new HashMap<>();

        // Add dynamic conditions
        if (StringUtils.isNotBlank(workerName)) {
            queryBuilder.append(" AND LOWER(p.constructionMember.name) LIKE :workerName");
            countQueryBuilder.append(" AND LOWER(p.constructionMember.name) LIKE :workerName");
            parameters.put("workerName", "%" + workerName.toLowerCase() + "%");
        }
        if (Objects.nonNull(paymentDateFrom)) {
            queryBuilder.append(" AND p.paymentDate >= :paymentDateFrom");
            countQueryBuilder.append(" AND p.paymentDate >= :paymentDateFrom");
            parameters.put("paymentDateFrom", paymentDateFrom);
        }
        if (Objects.nonNull(paymentDateTo)) {
            queryBuilder.append(" AND p.paymentDate <= :paymentDateTo");
            countQueryBuilder.append(" AND p.paymentDate <= :paymentDateTo");
            parameters.put("paymentDateTo", paymentDateTo);
        }

        // Add sorting dynamically (ONLY in queryBuilder, NOT in countQueryBuilder)
        if (pageable.getSort().isSorted()) {
            queryBuilder.append(" ORDER BY ");
            String orderBy = pageable.getSort().stream()
                .map(order -> "p." + order.getProperty() + " " + order.getDirection().name())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
            queryBuilder.append(orderBy);
        }

        // Create query
        Query query = entityManager.createQuery(queryBuilder.toString(), PaymentResponseExtendedDTO.class);
        Query countQuery = entityManager.createQuery(countQueryBuilder.toString());

        // Set parameters
        parameters.forEach(query::setParameter);
        parameters.forEach(countQuery::setParameter);

        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Execute query and get results
        List<PaymentResponseExtendedDTO> results = query.getResultList();
        long total = (long) countQuery.getSingleResult();

        // Return paginated result
        return new PageImpl<>(results, pageable, total);
    }
}
