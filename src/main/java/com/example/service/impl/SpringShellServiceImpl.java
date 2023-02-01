package com.example.service.impl;

import com.example.domain.*;
import com.example.dto.*;
import com.example.remote.CreateProductFamilyRequest;
import com.example.remote.CreateProductFamilyResponse;
import com.example.remote.RemoteWebClient;
import com.example.repository.*;
import com.example.service.SpringShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
@Transactional
public class SpringShellServiceImpl implements SpringShellService {


    @Autowired
    private SpringShellRepository shellEventRepository;

    @Autowired
    private RemoteWebClient remoteWebClient;

    @Autowired
    private ProductFamilyRepository productFamilyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantProductFamilyRepository tenantProductFamilyRepository;

    @Autowired
    private ResourceBundleRepository resourceBundleRepository;

    @Override
    public boolean saveCommandEventLog(CommandEventLogDTO dto) {
        CommandEventLogDomain domain = convertToCommandEventDomain(dto);
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

    @Override
    public boolean saveTenant(TenantDTO dto) {
        TenantDomain tenantDomain = convertTenantDTOToDomain(dto);
        tenantDomain = tenantRepository.save(tenantDomain);
        if (tenantDomain != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveProductFamily(ProductFamilyDTO dto) {
        ProductFamilyDomain productFamilyDomain = convertProductFamilyDTOToDomain(dto);
        productFamilyDomain = productFamilyRepository.save(productFamilyDomain);
        if (productFamilyDomain != null) {
            return true;
        }
        return false;
    }

    @Override
    public TenantDTO getTenantByName(String name) {
        TenantDomain domain = tenantRepository.findByTenantName(name);
        return convertTenantDomainToDTO(domain);
    }

    @Override
    public ProductFamilyDTO getProductFamilyByName(String name) {
        ProductFamilyDomain domain = productFamilyRepository.findByProductFamilyName(name);
        return convertProductFamilyDomainToDTO(domain);
    }

    @Override
    public boolean saveCommandEventLog(String commandName, String value, String status) {
        CommandEventLogDTO logDTO = new CommandEventLogDTO();
        logDTO.setCommandName(commandName);
        logDTO.setCommandValue(value);
        logDTO.setCreatedDate(new Date());
        logDTO.setExecutedDate(new Date());
        logDTO.setStatus(status);

        return saveCommandEventLog(logDTO);
    }

    @Override
    public boolean saveTenantProductFamily(TenantProductFamilyDTO dto) {
        TenantProductFamilyDomain domain = convertTenantProFamilyDTOToDomain(dto);
        domain = tenantProductFamilyRepository.save(domain);
        if (domain != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveResourceBundle(ResourceBundleDTO dto) {
        ResourceBundleDomain domain = convertResourceBundleDTOToDomain(dto);
        domain = resourceBundleRepository.save(domain);
        if (domain != null) {
            return true;
        }
        return false;
    }

    @Override
    public ResourceBundleDTO getResourceBundleByTenantAndProductFamily(String tenantName, String productFamilyName) {
        ResourceBundleDomain domain = resourceBundleRepository.findByTenantNameAndProductFamilyName(tenantName, productFamilyName);
        return convertResourceBundleDomainToDTO(domain);
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

    private TenantDomain convertTenantDTOToDomain(TenantDTO dto) {
        TenantDomain domain = new TenantDomain();
        domain.setId(dto.getId());
        domain.setTenantName(dto.getTenantName());
        domain.setCreatedDate(dto.getCreatedDate());

        return domain;
    }

    private ProductFamilyDomain convertProductFamilyDTOToDomain(ProductFamilyDTO dto) {
        ProductFamilyDomain domain = new ProductFamilyDomain();
        domain.setId(dto.getId());
        domain.setProductFamilyName(dto.getProductFamilyName());
        domain.setCreatedDate(dto.getCreatedDate());

        return domain;
    }

    private String convertDateToStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(date);
    }

    private TenantDTO convertTenantDomainToDTO(TenantDomain domain) {
        if (domain != null) {
            TenantDTO dto = new TenantDTO();
            dto.setId(domain.getId());
            dto.setTenantName(domain.getTenantName());
            dto.setCreatedDate(domain.getCreatedDate());

            return dto;
        }

        return null;
    }

    private ProductFamilyDTO convertProductFamilyDomainToDTO(ProductFamilyDomain domain) {
        if (domain != null) {
            ProductFamilyDTO dto = new ProductFamilyDTO();
            dto.setId(domain.getId());
            dto.setProductFamilyName(domain.getProductFamilyName());
            dto.setCreatedDate(domain.getCreatedDate());

            return dto;
        }

        return null;
    }

    private TenantProductFamilyDomain convertTenantProFamilyDTOToDomain(TenantProductFamilyDTO dto) {
        if (dto != null) {
            TenantProductFamilyDomain domain = new TenantProductFamilyDomain();
            domain.setId(dto.getId());
            domain.setTenantName(dto.getTenantName());
            domain.setProductFamilyName(dto.getProductFamilyName());
            domain.setCreatedDate(dto.getCreatedDate());

            return domain;
        }
        return null;
    }

    private ResourceBundleDomain convertResourceBundleDTOToDomain(ResourceBundleDTO dto) {
        if (dto != null) {
            ResourceBundleDomain domain = new ResourceBundleDomain();
            domain.setId(dto.getId());
            domain.setTenantName(dto.getTenantName());
            domain.setProductFamilyName(dto.getProductFamilyName());
            domain.setCoreCapabilities(dto.getCoreCapabilities());
            domain.setCreatedDate(dto.getCreatedDate());
            return domain;
        }
        return null;
    }

    private ResourceBundleDTO convertResourceBundleDomainToDTO(ResourceBundleDomain domain) {
        if (domain != null) {
            ResourceBundleDTO dto = new ResourceBundleDTO();
            dto.setId(domain.getId());
            dto.setTenantName(domain.getTenantName());
            dto.setProductFamilyName(domain.getProductFamilyName());
            dto.setCoreCapabilities(domain.getCoreCapabilities());
            dto.setCreatedDate(domain.getCreatedDate());

            return dto;
        }
        return null;
    }
}
