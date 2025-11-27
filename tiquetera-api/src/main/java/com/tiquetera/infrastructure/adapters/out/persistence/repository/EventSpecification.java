package com.tiquetera.infrastructure.adapters.out.persistence.repository;

import com.tiquetera.infrastructure.adapters.out.persistence.entity.EventEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<EventEntity> withDynamicFilters(String city, String category, LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("venue").get("city"), city));
            }

            // Filtro por Categoría
            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            // Filtro por Fecha
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), startDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}