package com.ecommerce.library.repository;

import com.ecommerce.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);

    @Query(nativeQuery = true,
            value = "SELECT r.name FROM roles r " +
                    "INNER JOIN customers_roles cr ON r.role_id = cr.role_id "+
                    "INNER JOIN customers c ON cr.customer_id = cr.customer_id " +
                    "WHERE c.username = :username")
    List<String> findRolesByUsername(@Param("username") String username);
}
