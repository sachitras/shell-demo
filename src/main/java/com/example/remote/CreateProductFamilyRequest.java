package com.example.remote;

import lombok.Data;

@Data
public class CreateProductFamilyRequest {
	
	private String product_id;
	private String code;
	private String[] supported_denominations;
	private TMParamDetail[] params;
	private String[] tags;
	private String display_name;
	private String description;
	private String summary;

}
