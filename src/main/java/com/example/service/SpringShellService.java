package com.example.service;

import com.example.dto.CommandEventLogDTO;
import com.example.dto.CommandInfoDTO;

public interface SpringShellService {

    public boolean saveCommandEventLog(CommandEventLogDTO dto);

    public boolean sendCommand(CommandInfoDTO dto);
}
