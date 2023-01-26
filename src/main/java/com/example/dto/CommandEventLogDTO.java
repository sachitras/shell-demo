package com.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommandEventLogDTO {

    private int id;
    private String commandName;
    private String commandValue;
    private Date executedDate;
    private String status;
    private Date createdDate;

}
