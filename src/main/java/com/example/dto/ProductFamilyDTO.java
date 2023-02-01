package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductFamilyDTO {

    private int id;
    private String productFamilyName;
    private Date createdDate;
}
