package com.example.command;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import com.example.dto.*;
import com.example.fto.ProductFamilyCapabilityFTO;
import com.example.fto.ProductFamilyFTO;
import com.example.fto.TenantFTO;
import com.example.remote.RemoteK8Client;
import com.example.service.SpringShellService;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.shell.Availability;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Component;

@Component
@ShellComponent
public class SSHCommand implements CommandMarker {

	@Autowired
	private SpringShellService shellService;

	Logger LOGGER = Logger.getLogger(SSHCommand.class.getName());

	private boolean tenantCreated;
	private boolean productFamilyCreated;

	private String vTenantName;
	private String vProductFamilyName;

	@Autowired
	private RemoteK8Client k8Client;


	private List<String> coreCapabilityList = new ArrayList<>();

	@ShellMethod(key = {"px tenant create"})
	public void createTenant(@ShellOption(value = { "name" }, help = "Tenant name", optOut = true) String tenantName)  {
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
				tenant.setId(UUID.randomUUID().toString());
				tenant.setTenantName(tenantName);
				tenant.setCreatedDate(new Date());
				boolean flag = shellService.saveTenant(tenant);
				if(flag) {
					LOGGER.info("Tenant created successfully.");
				}

				// Save the event log
				shellService.saveCommandEventLog("create-tenant --name", tenantName, "Completed");

				TenantDTO savedTenant = shellService.getTenantById(tenant.getId());
				LOGGER.info("-------------------------------------------------------");
				LOGGER.info("Tenant ID   : " + savedTenant.getId());
				LOGGER.info("Tenant Name :" + savedTenant.getTenantName());

				tenantCreated = true;
				vTenantName = tenantName;
			} else {
				LOGGER.info("Tenant already exists. Please give a different tenant name.");
			}

		} else {
			LOGGER.info("Please enter a valid tenant name");
		}


	}

	@ShellMethod(key = { "px product-family create"})
	public void createProduct(@ShellOption(value = { "name" }, help = "Product family name", optOut = true) String productFamilyName,
							  @ShellOption(value = { "tenant" }, help = "Tenant Id", optOut = true) String tenantId,
							  @ShellOption(value = { "smart-contract" }, help = "Smart Contract URL") String smartContract)  {
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
				dto.setId(UUID.randomUUID().toString());
				dto.setProductFamilyName(productFamilyName);
				dto.setTenantId(tenantId);
				dto.setSmartContractName(smartContract);
				dto.setCreatedDate(new Date());
				shellService.saveProductFamily(dto);

				// Call the external API
//				CommandInfoDTO infoDTO = new CommandInfoDTO();
//				infoDTO.setCommandValue(productFamilyName);
//				boolean flag = shellService.sendCommand(infoDTO);
//				if (flag) {
//					LOGGER.info("Request processed successfully.");
//				}



				LOGGER.info("Product family created successfully.");
				// Save the event log
				shellService.saveCommandEventLog("create-product-family --name", productFamilyName, "Completed");

				ProductFamilyDTO savedDTO = shellService.getProductFamilyById(dto.getId());
				LOGGER.info("-------------------------------------------------------");
				LOGGER.info("Product Family ID   :" + savedDTO.getId());
				LOGGER.info("Product Family Name :" + savedDTO.getProductFamilyName());
				LOGGER.info("Tenant Id           :" + savedDTO.getTenantId());
				LOGGER.info("Smart Contract      :" + savedDTO.getSmartContractName());

			} else {
				LOGGER.info("Product family already exists. Please give a different product family name.");
			}

		} else {
			LOGGER.info("Please enter a valid product family");
		}
	}

	@ShellMethod(key = { "px capability add"})
	public void addCapability(@ShellOption(value = { "select" }, help = "Capabilities separated by commas", optOut = true) String capabilities,
							  @ShellOption(value = { "product-family" }, help = "Product family id", optOut = true) String productFamilyId) {
		if (capabilities != null && capabilities.length() > 0) {
			LOGGER.info("Adding capabilities to product family...");
			String[] capabilityArray = capabilities.split(",");
			List<String> capabilityList = Arrays.asList(capabilityArray);
			for (String capability: capabilityList) {
				ProductFamilyCapabilityDTO capabilityDTO = new ProductFamilyCapabilityDTO();
				capabilityDTO.setId(UUID.randomUUID().toString());
				capabilityDTO.setProductFamilyId(productFamilyId);
				capabilityDTO.setCapabilityName(capability);
				capabilityDTO.setCreatedDate(new Date());

				ProductCapabilityDTO capDTO = shellService.getProductCapabilityByName(capability);
				if (capDTO != null) {
					capabilityDTO.setRepoURL(capDTO.getRepoURL());
				}
				shellService.saveProductFamilyCapability(capabilityDTO);
			}
			LOGGER.info("Product family capabilities were added.");
			LOGGER.info("-------------------------------------------------------");
			LOGGER.info("Product Family ID :" + productFamilyId);
			LOGGER.info("Capabilities      :" + capabilities);

		} else {
			LOGGER.info("Please enter a valid core capability");
		}

	}

	@ShellMethod(key = { "px provision"})
	public void provisionProductFamily(@ShellOption(value = { "product-family" }, help = "Product Family Id", optOut = true) String productFamilyId,
							  @ShellOption(value = { "env" }, help = "Environment", optOut = true) String env) {
		if (productFamilyId != null && productFamilyId.length() > 0) {
			LOGGER.info("Provisioning product family...");
			ProductFamilyEnvDTO dto = new ProductFamilyEnvDTO();
			dto.setId(UUID.randomUUID().toString());
			dto.setProductFamilyId(productFamilyId);
			dto.setEnvName(env);
			dto.setCreatedDate(new Date());

			ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyById(productFamilyId);
			boolean isNamespaceAvailable = k8Client.isNamespaceAvailable(productFamilyDTO.getProductFamilyName());

			delay(3000);

			if (!isNamespaceAvailable) {
				LOGGER.info("Creating namespace " + productFamilyDTO.getProductFamilyName() + "...");
				delay(3000);
				k8Client.createNamespace(productFamilyDTO.getProductFamilyName());
				LOGGER.info("Namespace created.");
				isNamespaceAvailable = true;

			} else {
				LOGGER.info("Namespace already exists.");
			}

			if (isNamespaceAvailable) {
				delay(3000);
				LOGGER.info("Deploying services...");
				createK8Services(productFamilyId);
				boolean flag = shellService.saveProductFamilyEnv(dto);
				if (flag) {
					LOGGER.info("Product family was provisioned successfully.");
					LOGGER.info("-------------------------------------------------------");
					LOGGER.info("Product Family :" + productFamilyId);
					LOGGER.info("Environment    :" + env);
				}
				delay(3000);
				LOGGER.info("Services deployed successfully.");
			}

		} else {
			LOGGER.info("Please enter valid parameters.");
		}
	}

	@ShellMethod(key = { "px capability configure"})
	public void configureCapabilityAPIExt(@ShellOption(value = { "" }, help = "Capability Name", optOut = true) String capabilityName,
										  @ShellOption(value = { "product-family" }, help = "Product Family Id", optOut = true) String productFamilyId,
									    @ShellOption(value = { "api-ext" }, help = "API Extension URL", optOut = true) String apiExt,
										  @ShellOption(value = { "adaptor-ext" }, help = "Adaptor Extension URL", optOut = true) String adaptorExt) {
		if (productFamilyId != null && productFamilyId.length() > 0 && capabilityName != null && capabilityName.length() > 0) {
			if (apiExt != null && apiExt.length() > 0) {
				LOGGER.info("Configuring the capability...");
				ProductCapabilityConfigDTO dto = new ProductCapabilityConfigDTO();
				dto.setId(UUID.randomUUID().toString());
				dto.setProductFamilyId(productFamilyId);
				dto.setCapabilityName(capabilityName);
				dto.setExtensionType("api-ext");
				dto.setExtURL(apiExt);
				dto.setCreatedDate(new Date());

				boolean flag = shellService.saveProductCapabilityConfig(dto);
				if (flag) {
					LOGGER.info("Capability was configured successfully");
					LOGGER.info("-------------------------------------------------------");
					LOGGER.info("Product family Id: " + productFamilyId);
					LOGGER.info("Capability name  : " + capabilityName);
					LOGGER.info("Extension type   : api-ext");
					LOGGER.info("Ext URL          : " + apiExt);

				}

			} else if (adaptorExt != null && adaptorExt.length() > 0) {
				LOGGER.info("Configuring the capability...");
				ProductCapabilityConfigDTO dto = new ProductCapabilityConfigDTO();
				dto.setId(UUID.randomUUID().toString());
				dto.setProductFamilyId(productFamilyId);
				dto.setCapabilityName(capabilityName);
				dto.setExtensionType("adaptor-ext");
				dto.setExtURL(adaptorExt);
				dto.setCreatedDate(new Date());

				boolean flag = shellService.saveProductCapabilityConfig(dto);
				if (flag) {
					LOGGER.info("Capability was configured successfully");
					LOGGER.info("-------------------------------------------------------");
					LOGGER.info("Product family Id: " + productFamilyId);
					LOGGER.info("Capability name  : " + capabilityName);
					LOGGER.info("Extension type   : adaptor-ext");
					LOGGER.info("Ext URL          : " + adaptorExt);

				}

			}
		} else {
			LOGGER.info("Please enter valid parameters.");
		}
	}

	@ShellMethod(key = { "px tenant list"})
	public void listAllTenants() {
		List<TenantDTO> tenantDTOList = shellService.getAllTenants();

		if (tenantDTOList != null) {
			List<TenantFTO> ftoList = new ArrayList<>();
			for (TenantDTO dto : tenantDTOList) {
				TenantFTO fto = new TenantFTO();
				fto.setId(dto.getId());
				fto.setTenantName(dto.getTenantName());
				ftoList.add(fto);
			}

			LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
			headers.put("id", "Tenant ID");
			headers.put("tenantName", "Tenant Name");

			TableModel model = new BeanListTableModel<>(ftoList, headers);
			TableBuilder tableBuilder = new TableBuilder(model);

			tableBuilder.addFullBorder(BorderStyle.fancy_light);
			String content = tableBuilder.build().render(80);
			LOGGER.info(content);

		}
	}

	@ShellMethod(key = { "px product-family list"})
	public void listProductFamilyForTenantId(@ShellOption(value = { "tenant" }, help = "Tenant Id", optOut = true) String tenantId) {
		if (tenantId != null && tenantId.length() > 0) {
			List<ProductFamilyDTO> dtoList = shellService.getProductFamilyByTenantId(tenantId);
			if (dtoList != null) {
				List<ProductFamilyFTO> ftoList = new ArrayList<>();
				for (ProductFamilyDTO dto : dtoList) {
					ProductFamilyFTO fto = new ProductFamilyFTO();
					fto.setId(dto.getId());
					fto.setProductFamilyName(dto.getProductFamilyName());
					fto.setTenantId(dto.getTenantId());
					fto.setSmartContractName(dto.getSmartContractName());

					ftoList.add(fto);
				}

				LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
				headers.put("id", "Product Family ID");
				headers.put("productFamilyName", "Product Family Name");
				headers.put("tenantId", "Tenant Id");
				headers.put("smartContractName", "Smart Contract Name");

				TableModel model = new BeanListTableModel<>(ftoList, headers);
				TableBuilder tableBuilder = new TableBuilder(model);

				tableBuilder.addFullBorder(BorderStyle.fancy_light);
				String content = tableBuilder.build().render(80);
				LOGGER.info(content);

			}
		} else {
			LOGGER.info("Please enter a valid parameter.");
		}
	}

	@ShellMethod(key = { "px capability list"})
	public void listCapabilities(@ShellOption(value = { "product-family" }, help = "Product Family Id") String productFamilyId) {
		if (productFamilyId != null && productFamilyId.length() > 0) {
			List<ProductFamilyCapabilityDTO> dtoList = shellService.getProductFamilyCapabilitiesByProductFamily(productFamilyId);
			if (dtoList != null) {
				List<ProductFamilyCapabilityFTO> ftoList = new ArrayList<>();
				for (ProductFamilyCapabilityDTO dto : dtoList) {
					ProductFamilyCapabilityFTO fto = new ProductFamilyCapabilityFTO();
					fto.setId(dto.getId());
					fto.setCapabilityName(dto.getCapabilityName());
					fto.setProductFamilyId(dto.getProductFamilyId());
					fto.setRepoURL(dto.getRepoURL());
					ftoList.add(fto);
				}
				LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
				headers.put("id", "Id");
				headers.put("productFamilyId", "Product Family Id");
				headers.put("capabilityName", "Capability name");
				headers.put("repoURL", "Repository URL");

				TableModel model = new BeanListTableModel<>(ftoList, headers);
				TableBuilder tableBuilder = new TableBuilder(model);

				tableBuilder.addFullBorder(BorderStyle.fancy_light);
				String content = tableBuilder.build().render(80);
				LOGGER.info(content);
			}
		} else {
			List<ProductCapabilityDTO> dtoList = shellService.getAllCapabilities();
			if (dtoList != null) {
				LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
				headers.put("capabilityId", "Capability Id");
				headers.put("capabilityName", "Capability Name");
				headers.put("repoURL", "Repository URL");

				TableModel model = new BeanListTableModel<>(dtoList, headers);
				TableBuilder tableBuilder = new TableBuilder(model);

				tableBuilder.addFullBorder(BorderStyle.fancy_light);
				String content = tableBuilder.build().render(80);
				LOGGER.info(content);
			}
		}
	}

	@ShellMethod(key = { "px tenant describe"})
	public void describeTenant(@ShellOption(value = { "" }, help = "Tenant Id", optOut = true) String tenantId) {
		if (tenantId != null && tenantId.length() > 0) {
			TenantDTO tenantDTO = shellService.getTenantById(tenantId);
			if (tenantDTO != null) {
				LOGGER.info("---------------------------------------");
				LOGGER.info("Tenant Id     : " + tenantDTO.getId());
				LOGGER.info("Tenant Name   : " + tenantDTO.getTenantName());
			}
		} else {
			LOGGER.info("Please enter a valid parameter.");
		}
	}

	@ShellMethod(key = { "px product-family describe"})
	public void describeProductFamily(@ShellOption(value = { "" }, help = "Product Family Id", optOut = true) String productFamilyId) {
		if (productFamilyId != null && productFamilyId.length() > 0) {

			ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyById(productFamilyId);
			if (productFamilyDTO != null) {
				LOGGER.info("---------------------------------------");
				LOGGER.info("Product Family Id    : " + productFamilyDTO.getId());
				LOGGER.info("Tenant Id            : " + productFamilyDTO.getTenantId());
				LOGGER.info("Product Family Name  : " + productFamilyDTO.getProductFamilyName());
				LOGGER.info("Smart Contract Name  : " + productFamilyDTO.getSmartContractName());
			}


		} else {
			LOGGER.info("Please enter a valid parameter.");
		}
	}

	@ShellMethod(key = { "px capability describe"})
	public void describeCapabilities(@ShellOption(value = { "" }, help = "Capability Name", optOut = true) String capabilityName,
									 @ShellOption(value = { "product-family-id" }, help = "Product Family Id", optOut = true) String productFamilyId) {
		if (productFamilyId != null && productFamilyId.length() > 0) {
			if (capabilityName != null && capabilityName.length() > 0) {
				List<ProductCapabilityConfigDTO> capabilityConfigDTOS = shellService.getCapabilityConfigDetails(capabilityName, productFamilyId);
				for (ProductCapabilityConfigDTO dto : capabilityConfigDTOS) {
					LOGGER.info("-------------------------------------------------");
					LOGGER.info("Product Family Id         : " + dto.getProductFamilyId());
					LOGGER.info("Capability Name           : " + dto.getCapabilityName());
					LOGGER.info("Extension Type            : " + dto.getExtensionType());
					LOGGER.info("Extension URL             : " + dto.getExtURL());
				}

			} else {
				LOGGER.info("Please enter a valid parameter.");
			}

		} else if (capabilityName != null && capabilityName.length() > 0) {
			ProductCapabilityDTO productCapabilityDTO = shellService.getProductCapabilityByName(capabilityName);
			if (productCapabilityDTO != null) {
				LOGGER.info("-------------------------------------------------");
				LOGGER.info("Capability Id     : " + productCapabilityDTO.getCapabilityId());
				LOGGER.info("Capability Name   : " + productCapabilityDTO.getCapabilityName());
			}

		} else {
			LOGGER.info("Please enter a valid parameter.");
		}
	}




//	public Availability createProductAvailability() {
//		return tenantCreated ? Availability.available() : Availability.unavailable("Please create a tenant first.");
//	}


	/********************************** Private methods **************************************/

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

	private boolean createK8Services(String productFamilyId) {
		ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyById(productFamilyId);
		if (productFamilyDTO != null) {
			List<ProductCapabilityConfigDTO> capabilityConfigDTOS = shellService.getCapabilityConfigDetails(productFamilyId);
			if (capabilityConfigDTOS != null) {
				for (ProductCapabilityConfigDTO configDTO : capabilityConfigDTOS) {
					k8Client.runDeployment(configDTO.getCapabilityName(), productFamilyDTO.getProductFamilyName());
				}

			}
		}

		return false;
	}

	private void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}


}
