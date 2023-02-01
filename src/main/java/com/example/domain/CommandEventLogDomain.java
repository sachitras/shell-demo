package com.example.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SHELL_COMMAND_EVENT_LOG")
public class CommandEventLogDomain {

    private long id;
    private String commandName;
    private String commandValue;
    private String executedDate;
    private String status;
    private String createdDate;

    /**
     * @return the id
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHELL_COMMAND_EVENT_LOG_SEQUENCE")
    @SequenceGenerator(name = "SHELL_COMMAND_EVENT_LOG_SEQUENCE", sequenceName = "SHELL_COMMAND_EVENT_LOG_SEQUENCE", allocationSize = 1, initialValue = 1)
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the commandName
     */
    @Column(name = "COMMAND_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getCommandName() {
        return commandName;
    }

    /**
     * @param commandName the commandName to set
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * @return the commandValue
     */
    @Column(name = "COMMAND_VALUE", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getCommandValue() {
        return commandValue;
    }

    /**
     * @param commandValue the commandValue to set
     */
    public void setCommandValue(String commandValue) {
        this.commandValue = commandValue;
    }

    /**
     * @return the executedDate
     */
    @Column(name = "EXECUTED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public String getExecutedDate() {
        return executedDate;
    }

    /**
     * @param executedDate the executedDate to set
     */
    public void setExecutedDate(String executedDate) {
        this.executedDate = executedDate;
    }

    /**
     * @return the status
     */
    @Column(name = "STATUS", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the createdDate
     */
    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = true, precision = 0)
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
