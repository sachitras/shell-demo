package com.example.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT_CAPABILITY")
public class ProductCapabilityDomain {

    private String capabilityId;
    private String capabilityName;
    private String repoURL;

    @Id
    @Column(name = "CAPABILITY_ID")
    public String getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(String capabilityId) {
        this.capabilityId = capabilityId;
    }

    @Column(name = "CAPABILITY_NAME")
    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }

    @Column(name = "REPO_URL")
    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }
}
