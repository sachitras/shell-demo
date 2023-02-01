package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESOURCE_BUNDLE")
public class ResourceBundleDomain {
    private int id;
    private String tenantName;
    private String productFamilyName;
    private String coreCapabilities;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESOURCE_BUNDLE_SEQUENCE")
    @SequenceGenerator(name = "RESOURCE_BUNDLE_SEQUENCE", sequenceName = "RESOURCE_BUNDLE_SEQUENCE", allocationSize = 1, initialValue = 1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "TENANT_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Column(name = "PRODUCT_FAMILY_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getProductFamilyName() {
        return productFamilyName;
    }

    public void setProductFamilyName(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    @Column(name = "CORE_CAPABILITIES", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getCoreCapabilities() {
        return coreCapabilities;
    }

    public void setCoreCapabilities(String coreCapabilities) {
        this.coreCapabilities = coreCapabilities;
    }

    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
