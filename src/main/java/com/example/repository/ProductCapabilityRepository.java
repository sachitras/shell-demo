package com.example.repository;

import com.example.domain.ProductCapabilityDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCapabilityRepository extends JpaRepository<ProductCapabilityDomain, String> {

    public ProductCapabilityDomain findByCapabilityName(String capabilityName);
}
