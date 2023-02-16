package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.util.Date;

@Entity
@Table(name = "PRODUCT_FAMILY_CAPABILITY_CONFIG")
public class ProductCapabilityConfigDomain {

    private String id;
    private String capabilityName;
    private String productFamilyId;
    private String extensionType;
    private String extURL;
    private Date createdDate;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CAPABILITY_NAME")
    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }

    @Column(name = "PRODUCT_FAMILY_ID")
    public String getProductFamilyId() {
        return productFamilyId;
    }

    public void setProductFamilyId(String productFamilyId) {
        this.productFamilyId = productFamilyId;
    }

    @Column(name = "EXTENSION_TYPE")
    public String getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }

    @Column(name = "EXT_URL")
    public String getExtURL() {
        return extURL;
    }

    public void setExtURL(String extURL) {
        this.extURL = extURL;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
