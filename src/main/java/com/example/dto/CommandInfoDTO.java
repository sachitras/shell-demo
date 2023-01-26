package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommandInfoDTO {

    private int id;
    private String commandName;
    private String commandValue;
    private Date executedDate;
}
