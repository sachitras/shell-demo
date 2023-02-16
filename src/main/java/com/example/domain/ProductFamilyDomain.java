package com.example.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_FAMILY")
public class ProductFamilyDomain {

    private String productFamilyId;
    private String productFamilyName;

    private String tenantId;
    private String smartContractName;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getProductFamilyId() {
        return productFamilyId;
    }

    public void setProductFamilyId(String id) {
        this.productFamilyId = id;
    }

    @Column(name = "PRODUCT_FAMILY_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getProductFamilyName() {
        return productFamilyName;
    }

    public void setProductFamilyName(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "TENANT_ID")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "SMART_CONTRACT_NAME")
    public String getSmartContractName() {
        return smartContractName;
    }

    public void setSmartContractName(String smartContractName) {
        this.smartContractName = smartContractName;
    }

}
