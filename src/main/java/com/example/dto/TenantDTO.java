package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TenantDTO {

    private int id;
    private String tenantName;
    private Date createdDate;

}
