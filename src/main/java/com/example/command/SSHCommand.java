package com.example.command;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import com.example.dto.CommandEventLogDTO;
import com.example.dto.CommandInfoDTO;
import com.example.service.SpringShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class SSHCommand implements CommandMarker {

	@Autowired
	private SpringShellService shellService;

	Logger LOGGER = Logger.getLogger(SSHCommand.class.getName());
	
	@CliCommand(value = "ssh", help = "Connects to a remote server")
	public void ssh(@CliOption(key = "s") String remoteServer, @CliOption(key = "p") String port) {
		LOGGER.info("Logged to remote server: " + remoteServer + " port " + port);		
	}
	
	@CliCommand(value = { "hello", "hi" }, help = "Greats an user.")
    public void webGet(@CliOption(key = { "n", "name" }, help = "User name") String name)  {
    	LOGGER.info("Hello.... " + name);
    }

	@CliCommand(value = { "create product"}, help = "Creates a product")
	public void createProduct(@CliOption(key = { "name" }, help = "Product name") String productName)  {
		LOGGER.info("Creating product " + productName + " ...");
		try {
			Thread.sleep(4000);
		} catch (Exception e) {}

		// Save event log in the h2 DB
		CommandEventLogDTO logDTO = new CommandEventLogDTO();
		logDTO.setCommandName("create product");
		logDTO.setCommandValue(productName);
		logDTO.setCreatedDate(new Date());
		logDTO.setExecutedDate(new Date());
		logDTO.setStatus("Initiated");

		shellService.saveCommandEventLog(logDTO);
		LOGGER.info("Executing the command event...");

		try {
			Thread.sleep(3000);
		} catch (Exception e) {}

		// Call the external API
		CommandInfoDTO infoDTO = new CommandInfoDTO();
		infoDTO.setCommandValue(productName);
		boolean flag = shellService.sendCommand(infoDTO);
		if (flag) {
			LOGGER.info("Request processed successfully.");
		}

		// Save event in the log in the h2 DB
		logDTO.setCreatedDate(new Date());
		logDTO.setExecutedDate(new Date());
		logDTO.setStatus("Completed");
		shellService.saveCommandEventLog(logDTO);

		LOGGER.info("Product creation completed.");
	}

}
