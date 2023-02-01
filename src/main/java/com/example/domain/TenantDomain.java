package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TENANT")
public class TenantDomain {

    private int id;
    private String tenantName;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TENANT_SEQUENCE")
    @SequenceGenerator(name = "TENANT_SEQUENCE", sequenceName = "TENANT_SEQUENCE", allocationSize = 1, initialValue = 1)
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

    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
