package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCapabilityConfigDTO {

    private String id;
    private String capabilityName;
    private String productFamilyId;
    private String extURL;
    private Date createdDate;
    private String extensionType;

}
