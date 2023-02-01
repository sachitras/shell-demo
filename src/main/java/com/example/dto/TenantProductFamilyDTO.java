package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TenantProductFamilyDTO {

    private int id;
    private String tenantName;
    private String productFamilyName;
    private Date createdDate;
}
