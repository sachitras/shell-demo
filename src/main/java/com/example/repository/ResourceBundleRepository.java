package com.example.repository;

import com.example.domain.ResourceBundleDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceBundleRepository extends JpaRepository<ResourceBundleDomain, Integer> {

    ResourceBundleDomain findByTenantNameAndProductFamilyName(String tenantName, String productFamilyName);
}
