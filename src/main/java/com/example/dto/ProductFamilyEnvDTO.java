package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFamilyEnvDTO {

    private String id;
    private String productFamilyId;
    private String envName;
    private Date createdDate;
}
