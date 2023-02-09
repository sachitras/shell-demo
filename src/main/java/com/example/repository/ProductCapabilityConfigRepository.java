package com.example.repository;

import com.example.domain.ProductCapabilityConfigDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCapabilityConfigRepository extends JpaRepository<ProductCapabilityConfigDomain, String> {
    public List<ProductCapabilityConfigDomain> findByCapabilityNameAndProductFamilyId(String capabilityName, String productFamilyId);

    public List<ProductCapabilityConfigDomain> findByProductFamilyId(String productFamilyId);
}
