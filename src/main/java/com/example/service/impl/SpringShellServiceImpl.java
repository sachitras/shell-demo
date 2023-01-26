package com.example.service.impl;

import com.example.domain.CommandEventLogDomain;
import com.example.dto.CommandEventLogDTO;
import com.example.dto.CommandInfoDTO;
import com.example.remote.CreateProductFamilyRequest;
import com.example.remote.CreateProductFamilyResponse;
import com.example.remote.RemoteWebClient;
import com.example.repository.SpringShellRepository;
import com.example.service.SpringShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class SpringShellServiceImpl implements SpringShellService {


    @Autowired
    SpringShellRepository shellEventRepository;

    @Autowired
    RemoteWebClient remoteWebClient;

    @Override
    public boolean saveCommandEventLog(CommandEventLogDTO dto) {
        CommandEventLogDomain domain = convertToCommandEventDomain(dto);
        System.out.println("Saving :"+ domain.getCommandName());
        CommandEventLogDomain savedDomain = shellEventRepository.save(domain);
        if (savedDomain != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean sendCommand(CommandInfoDTO dto) {
        CreateProductFamilyRequest req = new CreateProductFamilyRequest();
        req.setProductFamilyName(dto.getCommandValue());
        CreateProductFamilyResponse res = remoteWebClient.createAccount(req);
        return res.equals("SUCCESS") ?  true : false;
    }



    /****************** Private methods *************************/

    private CommandEventLogDomain convertToCommandEventDomain(CommandEventLogDTO dto) {
        CommandEventLogDomain domain = new CommandEventLogDomain();
        domain.setCommandName(dto.getCommandName());
        domain.setCommandValue(dto.getCommandValue());
        domain.setCreatedDate(convertDateToStringDate(dto.getCreatedDate()));
        domain.setExecutedDate(convertDateToStringDate(dto.getCreatedDate()));
        domain.setStatus(dto.getStatus());

        return domain;
    }

    private String convertDateToStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(date);
    }
}
