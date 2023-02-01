package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_FAMILY")
public class ProductFamilyDomain {

    private int id;
    private String productFamilyName;
    private Date createdDate;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_FAMILY_SEQUENCE")
    @SequenceGenerator(name = "PRODUCT_FAMILY_SEQUENCE", sequenceName = "PRODUCT_FAMILY_SEQUENCE", allocationSize = 1, initialValue = 1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
