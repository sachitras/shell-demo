package com.example.service;

import com.example.dto.*;

public interface SpringShellService {

    public boolean saveCommandEventLog(CommandEventLogDTO dto);

    public boolean sendCommand(CommandInfoDTO dto);

    public boolean saveTenant(TenantDTO dto);

    public boolean saveProductFamily(ProductFamilyDTO dto);

    public TenantDTO getTenantByName(String name);

    public ProductFamilyDTO getProductFamilyByName(String name);

    public boolean saveCommandEventLog(String commandName, String value, String status);

    public boolean saveTenantProductFamily(TenantProductFamilyDTO dto);

    public boolean saveResourceBundle(ResourceBundleDTO dto);

    public ResourceBundleDTO getResourceBundleByTenantAndProductFamily(String tenantName, String productFamilyName);
}
