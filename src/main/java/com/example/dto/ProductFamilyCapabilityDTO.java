package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFamilyCapabilityDTO {

    private String id;
    private String productFamilyId;
    private String capabilityName;
    private Date createdDate;
    private String repoURL;

}
