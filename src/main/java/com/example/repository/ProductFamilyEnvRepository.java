package com.example.repository;

import com.example.domain.ProductFamilyEnvDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFamilyEnvRepository extends JpaRepository<ProductFamilyEnvDomain, String> {

}
