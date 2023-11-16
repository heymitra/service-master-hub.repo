package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.User;
import com.example.serviceprovider.model.enumeration.Role;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    default Page<User> searchAndFilterUsers(Role role,
                                            String name,
                                            String surname,
                                            String email,
                                            String sortBy,
                                            Pageable pageable) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (surname != null && !surname.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("surname"), "%" + surname + "%"));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.by(sortBy)));
    }

    Optional<User> findByEmail(String email);
}
