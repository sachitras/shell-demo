package com.example.repository;

import com.example.domain.TenantDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<TenantDomain, Integer> {

    public TenantDomain findByTenantName(String tenantName);
}
