package com.example.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TENANT")
public class TenantDomain {

    private String tenantId;
    private String tenantName;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String id) {
        this.tenantId = id;
    }
    @Column(name = "TENANT_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
