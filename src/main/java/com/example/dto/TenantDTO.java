package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TenantDTO {

    private String id;
    private String tenantName;
    private Date createdDate;

}