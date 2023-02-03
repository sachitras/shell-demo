package com.example.repository;

import com.example.domain.ProductFamilyDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamilyDomain, String> {

    public ProductFamilyDomain findByProductFamilyName(String productFamilyName);

    public ProductFamilyDomain findByProductFamilyId(String id);

    public List<ProductFamilyDomain> findByTenantId(String tenantId);


}
