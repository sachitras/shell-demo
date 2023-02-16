package com.example.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCapabilityIdClass implements Serializable {

    private String capabilityId;
    private String capabilityName;

    public ProductCapabilityIdClass(String capabilityId, String capabilityName) {
        this.capabilityId = capabilityId;
        this.capabilityName = capabilityName;
    }
}
