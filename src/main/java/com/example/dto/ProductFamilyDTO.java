package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFamilyDTO {

    private String id;
    private String productFamilyName;
    private String tenantId;
    private String smartContractName;
    private Date createdDate;
}