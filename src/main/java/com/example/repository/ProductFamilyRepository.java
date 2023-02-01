package com.example.repository;

import com.example.domain.ProductFamilyDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamilyDomain, Integer> {

    public ProductFamilyDomain findByProductFamilyName(String productFamilyName);
}
