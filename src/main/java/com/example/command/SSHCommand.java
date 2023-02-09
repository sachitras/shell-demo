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
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.shell.Availability;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Component;

@Component
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

	@CliCommand(value = { "px tenant create"}, help = "Creates a Tenant")
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
	@CliCommand(value = { "px product-family create"}, help = "Creates a product family")
	public void createProduct(@CliOption(key = { "name" }, help = "Product family name") String productFamilyName,
							  @CliOption(key = { "tenant" }, help = "Tenant Id") String tenantId,
							  @CliOption(key = { "smart-contract" }, help = "Smart Contract") String smartContract)  {
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

	@CliCommand(value = { "px capability add"}, help = "Adds a capability")
	public void addCapability(@CliOption(key = { "select" }, help = "Capabilities") String capabilities,
							  @CliOption(key = { "product-family" }, help = "Product family id") String productFamilyId) {
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

	@CliCommand(value = { "px provision"}, help = "Adds a capability")
	public void provisionProductFamily(@CliOption(key = { "product-family" }, help = "Product Family Id") String productFamilyId,
							  @CliOption(key = { "env" }, help = "Environment") String env) {
		if (productFamilyId != null && productFamilyId.length() > 0) {
			LOGGER.info("Provisioning product family...");
			ProductFamilyEnvDTO dto = new ProductFamilyEnvDTO();
			dto.setId(UUID.randomUUID().toString());
			dto.setProductFamilyId(productFamilyId);
			dto.setEnvName(env);
			dto.setCreatedDate(new Date());


			ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyById(productFamilyId);
			boolean isNamespaceAvailable = k8Client.isNamespaceAvailable(productFamilyDTO.getProductFamilyName());
			if (!isNamespaceAvailable) {
				k8Client.createNamespace(productFamilyDTO.getProductFamilyName());
				boolean flag = shellService.saveProductFamilyEnv(dto);
				if (flag) {
					LOGGER.info("Product family was provisioned successfully.");
					LOGGER.info("-------------------------------------------------------");
					LOGGER.info("Product Family :" + productFamilyId);
					LOGGER.info("Environment    :" + env);
				}
			} else {
				LOGGER.info("Provisioning failed. A namespace is available with the given product family name.");
			}

		} else {
			LOGGER.info("Please enter valid parameters.");
		}
	}

	@CliCommand(value = { "px capability configure"}, help = "Configures the capabilities")
	public void configureCapabilityAPIExt(@CliOption(key = { "capability-name" }, help = "Capability Name") String capabilityName,
										  @CliOption(key = { "product-family" }, help = "Product Family Id") String productFamilyId,
									    @CliOption(key = { "api-ext" }, help = "API Extension") String apiExt,
										  @CliOption(key = { "adaptor-ext" }, help = "Adaptor Extension") String adaptorExt) {
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

				if (createK8Service(productFamilyId, capabilityName)) {
					boolean flag = shellService.saveProductCapabilityConfig(dto);
					if (flag) {
						LOGGER.info("Capability was configured successfully");
						LOGGER.info("-------------------------------------------------------");
						LOGGER.info("Product family Id: " + productFamilyId);
						LOGGER.info("Capability name  : " + capabilityName);
						LOGGER.info("Extension type   : api-ext");
						LOGGER.info("Ext URL          : " + apiExt);

					}
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

				if (createK8Service(productFamilyId, capabilityName)) {
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

			}
		} else {
			LOGGER.info("Please enter valid parameters.");
		}
	}

	@CliCommand(value = { "px tenant list"}, help = "Lists all tenants")
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

	@CliCommand(value = { "px product-family list"}, help = "Lists product family for tenant id")
	public void listProductFamilyForTenantId(@CliOption(key = { "tenant" }, help = "Tenant Id") String tenantId) {
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

	@CliCommand(value = { "px capability list"}, help = "Lists all capabilities")
	public void listCapabilities(@CliOption(key = { "product-family" }, help = "Product Family Id") String productFamilyId) {
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

	@CliCommand(value = { "px tenant describe"}, help = "Describes the tenant for the given tenant id")
	public void describeTenant(@CliOption(key = { "tenant-id" }, help = "Tenant Id") String tenantId) {
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

	@CliCommand(value = { "px product-family describe"}, help = "Describes product family for the given product-family id")
	public void describeProductFamily(@CliOption(key = { "product-family-id" }, help = "Product Family Id") String productFamilyId) {
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

	@CliCommand(value = { "px capability describe"}, help = "Describe product family capabilities for given parameters")
	public void describeCapabilities(@CliOption(key = { "capability-name" }, help = "Capability Name") String capabilityName,
									 @CliOption(key = { "product-family-id" }, help = "Product Family Id") String productFamilyId) {
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

	private boolean createK8Service(String productFamilyId, String capabilityName) {
		ProductFamilyDTO productFamilyDTO = shellService.getProductFamilyById(productFamilyId);
		if (productFamilyDTO != null) {
			if (!k8Client.isServiceAlreadyAvailable(productFamilyDTO.getProductFamilyName(), capabilityName)) {
				k8Client.createService(productFamilyDTO.getProductFamilyName(), capabilityName);
				return true;
			} else {
				LOGGER.info("Capability configuration failed. Service is already available in the namespace.");
			}
		}

		return false;
	}

}
