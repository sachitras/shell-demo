package com.example.remote;

import lombok.Data;

@Data
public class ProductFamilyPayLoad {

    private String request_id;
    private String is_internal;
    private String migration_strategy;
    private CreateProductFamilyRequest product_version;

}
