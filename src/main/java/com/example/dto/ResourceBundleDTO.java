package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceBundleDTO {

    private int id;
    private String tenantName;
    private String productFamilyName;
    private String coreCapabilities;
    private Date createdDate;

}
