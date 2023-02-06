package com.example.repository;

import com.example.domain.ProductCapabilityDomain;
import com.example.domain.ProductFamilyCapabilityDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFamilyCapabilityRepository extends JpaRepository<ProductFamilyCapabilityDomain, String> {

    public List<ProductFamilyCapabilityDomain> findByCapabilityName(String capabilityName);

    public List<ProductFamilyCapabilityDomain> findByProductFamilyId(String productFamilyId);

    public List<ProductFamilyCapabilityDomain> findByProductFamilyIdAndCapabilityName(String productFamilyId, String capabilityName);
}
