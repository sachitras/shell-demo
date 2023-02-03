package com.example.repository;

import com.example.domain.ProductCapabilityConfigDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCapabilityConfigRepository extends JpaRepository<ProductCapabilityConfigDomain, String> {
}
