package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TENANT_PRODUCT_FAMILY")
public class TenantProductFamilyDomain {

    private int id;
    private String tenantName;
    private String productFamilyName;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TENANT_PRO_FAMILY_SEQUENCE")
    @SequenceGenerator(name = "TENANT_PRO_FAMILY_SEQUENCE", sequenceName = "TENANT_PRO_FAMILY_SEQUENCE", allocationSize = 1, initialValue = 1)

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

    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
