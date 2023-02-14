package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_FAMILY_CAPABILITY_ENV")
public class ProductFamilyEnvDomain {

    private String id;
    private String productFamilyId;

    private String envName;

    private Date createdDate;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "PRODUCT_FAMILY_ID")
    public String getProductFamilyId() {
        return productFamilyId;
    }

    public void setProductFamilyId(String productFamilyId) {
        this.productFamilyId = productFamilyId;
    }

    @Column(name = "ENV_NAME")
    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
