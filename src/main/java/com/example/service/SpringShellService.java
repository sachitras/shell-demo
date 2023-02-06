package com.example.service;

import com.example.domain.ProductCapabilityConfigDomain;
import com.example.domain.ProductCapabilityDomain;
import com.example.domain.ProductFamilyCapabilityDomain;
import com.example.dto.*;

import java.util.List;

public interface SpringShellService {

    public boolean saveCommandEventLog(CommandEventLogDTO dto);

    public boolean sendCommand(CommandInfoDTO dto);

    public boolean saveTenant(TenantDTO dto);

    public TenantDTO getTenantByName(String name);

    public TenantDTO getTenantById(String id);

    public boolean saveProductFamily(ProductFamilyDTO dto);

    public ProductFamilyDTO getProductFamilyByName(String name);

    public ProductFamilyDTO getProductFamilyById(String id);

    public boolean saveCommandEventLog(String commandName, String value, String status);

    public boolean saveTenantProductFamily(TenantProductFamilyDTO dto);

    public boolean saveResourceBundle(ResourceBundleDTO dto);

    public ResourceBundleDTO getResourceBundleByTenantAndProductFamily(String tenantName, String productFamilyName);

    public ProductCapabilityDTO getProductCapabilityByName(String capabilityName);

    public boolean saveProductFamilyCapability(ProductFamilyCapabilityDTO dto);

    public boolean saveProductFamilyEnv(ProductFamilyEnvDTO dto);

    public boolean saveProductCapabilityConfig(ProductCapabilityConfigDTO dto);

    public List<TenantDTO> getAllTenants();

    public List<ProductFamilyDTO> getProductFamilyByTenantId(String tenantId);

    public List<ProductCapabilityDTO> getAllCapabilities();

    public List<ProductFamilyCapabilityDTO> getProductFamilyCapabilities(String capabilityName);
    public List<ProductFamilyCapabilityDTO> getProductFamilyCapabilitiesByProductFamily(String productFamilyId);

    public List<ProductFamilyCapabilityDTO> getProductFamilyCapabilities(String productFamilyId, String capabilityName);

    public List<ProductCapabilityConfigDTO> getCapabilityConfigDetails(String capabilityName, String productFamilyId);
}
