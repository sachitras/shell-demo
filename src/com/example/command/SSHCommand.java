package com.example.command;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class SSHCommand implements CommandMarker {

	Logger LOGGER = Logger.getLogger(SSHCommand.class.getName());
	
	@CliCommand(value = "ssh", help = "Connects to a remote server")
	public void ssh(@CliOption(key = "s") String remoteServer, @CliOption(key = "p") String port) {
		LOGGER.info("Logged to remote server: " + remoteServer + " port " + port);		
	}
	
	@CliCommand(value = { "hello", "hi" }, help = "Greats an user.")
    public void webGet(@CliOption(key = { "n", "name" }, help = "User name") String name)  {
    	LOGGER.info("Hello.... " + name);
    }
}
