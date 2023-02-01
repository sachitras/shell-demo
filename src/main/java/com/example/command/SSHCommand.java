package com.example.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.example.dto.*;
import com.example.service.SpringShellService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.shell.Availability;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SSHCommand implements CommandMarker {

	@Autowired
	private SpringShellService shellService;

	Logger LOGGER = Logger.getLogger(SSHCommand.class.getName());

	private boolean tenantCreated;
	private boolean productFamilyCreated;

	private String vTenantName;
	private String vProductFamilyName;

	private List<String> coreCapabilityList = new ArrayList<>();

	@CliCommand(value = { "create-tenant"}, help = "Creates a Tenant")
	public void createTenant(@CliOption(key = { "name" }, help = "Tenant name") String tenantName)  {
		if (tenantName != null && tenantName.length() > 0) {
			TenantDTO tenantDTO = shellService.getTenantByName(tenantName);
			if (tenantDTO == null) {
				LOGGER.info("Creating tenant " + tenantName + " ...");
				try {
					Thread.sleep(4000);
				} catch (Exception e) {}

				// Save the event log
				shellService.saveCommandEventLog("create-tenant --name", tenantName, "Initiated");

				TenantDTO tenant = new TenantDTO();
				tenant.setTenantName(tenantName);
				tenant.setCreatedDate(new Date());
				boolean flag = shellService.saveTenant(tenant);
				if(flag) {
					LOGGER.info("Tenant saved successfully.");
				}

				// Save the event log
				shellService.saveCommandEventLog("create-tenant --name", tenantName, "Completed");
				tenantCreated = true;
				vTenantName = tenantName;
			} else {
				LOGGER.info("Tenant already exists. Please give a different tenant name.");
			}



		} else {
			LOGGER.info("Please enter a valid tenant name");
		}


	}
	@CliCommand(value = { "create-product-family"}, help = "Creates a product family")
	public void createProduct(@CliOption(key = { "name" }, help = "Product family name") String productFamilyName)  {
		if (productFamilyName != null && productFamilyName.length() > 0) {
			ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyByName(productFamilyName);
			if (productFamilyDTO == null) {
				LOGGER.info("Creating product family " + productFamilyName + " ...");
				try {
					Thread.sleep(4000);
				} catch (Exception e) {}

				// Save the event log
				shellService.saveCommandEventLog("create-product-family --name", productFamilyName, "Initiated");

				try {
					Thread.sleep(3000);
				} catch (Exception e) {}

				// Saving the record
				ProductFamilyDTO dto = new ProductFamilyDTO();
				dto.setProductFamilyName(productFamilyName);
				dto.setCreatedDate(new Date());
				shellService.saveProductFamily(dto);


				// Call the external API
				CommandInfoDTO infoDTO = new CommandInfoDTO();
				infoDTO.setCommandValue(productFamilyName);
				boolean flag = shellService.sendCommand(infoDTO);
				if (flag) {
					LOGGER.info("Request processed successfully.");
				}

				// Save the event log
				shellService.saveCommandEventLog("create-product-family --name", productFamilyName, "Completed");

				LOGGER.info("Product creation completed.");
				productFamilyCreated = true;
				vProductFamilyName = productFamilyName;

				// Bundling the tenant and product family
				TenantProductFamilyDTO tpfDTO = new TenantProductFamilyDTO();
				tpfDTO.setTenantName(vTenantName);
				tpfDTO.setProductFamilyName(vProductFamilyName);
				tpfDTO.setCreatedDate(new Date());
				boolean tpfFlag = shellService.saveTenantProductFamily(tpfDTO);


			} else {
				LOGGER.info("Product family already exists. Please give a different product family name.");
			}

		} else {
			LOGGER.info("Please enter a valid product family");
		}
	}

	@CliCommand(value = { "add-capability"}, help = "Adds a capability")
	public void addCapability(@CliOption(key = { "name" }, help = "Capability name") String capabilityName) {
		if (capabilityName != null && capabilityName.length() > 0) {
			coreCapabilityList.add(capabilityName);
			String coreCapabilitiesString = convertToStringWithPipes(coreCapabilityList);
			ResourceBundleDTO dto = new ResourceBundleDTO();
			dto.setTenantName(vTenantName);
			dto.setProductFamilyName(vProductFamilyName);
			dto.setCoreCapabilities(coreCapabilitiesString);
			dto.setCreatedDate(new Date());

			String logString = vTenantName + "-" + vProductFamilyName + "-" + coreCapabilitiesString;

			// Save the event log
			shellService.saveCommandEventLog("add-capability --name", logString, "Initiated");

			boolean flag = shellService.saveResourceBundle(dto);
			if (flag) {
				LOGGER.info("Core capability was added.");
			}

			// Save the event log
			shellService.saveCommandEventLog("add-capability --name", logString, "Completed");
			LOGGER.info("Bundle details :" + logString);



		} else {
			LOGGER.info("Please enter a valid core capability");
		}

	}

//	public Availability createProductAvailability() {
//		return tenantCreated ? Availability.available() : Availability.unavailable("Please create a tenant first.");
//	}


	@CliCommand(value = { "show-capabilities"}, help = "Shows the list of capabilities")
	public void addCap() {

		StringBuffer buff = new StringBuffer();
		buff.append("ACCNT");
		buff.append("\nLENDING");
		buff.append("\nPOSTING");

		LOGGER.info(buff.toString());

	}



	/****************** Private methods *************************/

	private String convertToStringWithPipes(List<String> valueList) {
		if (valueList != null && valueList.size() > 0) {
			StringBuffer finalString = new StringBuffer();
			for (String value : valueList) {
				if (finalString.length() > 0) {
					finalString.append("|");
				}
				finalString.append(value);
			}

			return finalString.toString();
		}
		return null;
	}

}
