package com.example.service.impl;

import com.example.domain.*;
import com.example.dto.*;
import com.example.fto.ProductFamilyCapabilityFTO;
import com.example.remote.CreateProductFamilyRequest;
import com.example.remote.CreateProductFamilyResponse;
import com.example.remote.RemoteWebClient;
import com.example.repository.*;
import com.example.service.SpringShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private ProductCapabilityRepository capabilityRepository;

    @Autowired
    private ProductFamilyEnvRepository productFamilyEnvRepository;

    @Autowired
    private ProductFamilyCapabilityRepository productFamilyCapabilityRepository;

    @Autowired
    private ProductCapabilityConfigRepository productCapabilityConfigRepository;


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
    public TenantDTO getTenantById(String id) {
        TenantDomain domain = tenantRepository.findByTenantId(id);
        return convertTenantDomainToDTO(domain);
    }

    @Override
    public ProductFamilyDTO getProductFamilyByName(String name) {
        ProductFamilyDomain domain = productFamilyRepository.findByProductFamilyName(name);
        return convertProductFamilyDomainToDTO(domain);
    }

    @Override
    public ProductFamilyDTO getProductFamilyById(String id) {
        ProductFamilyDomain domain = productFamilyRepository.findByProductFamilyId(id);
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

    @Override
    public ProductCapabilityDTO getProductCapabilityByName(String capabilityName) {
        ProductCapabilityDomain domain = capabilityRepository.findByCapabilityName(capabilityName);
        return convertProductCapabilityDomainToDTO(domain);
    }

    @Override
    public boolean saveProductFamilyCapability(ProductFamilyCapabilityDTO dto) {
        ProductFamilyCapabilityDomain domain = convertProductFamilyCapabilityDTOToDomain(dto);
       ProductFamilyCapabilityDomain savedDomain = productFamilyCapabilityRepository.save(domain);
       if (savedDomain != null) {
           return true;
       }
       return false;
    }

    @Override
    public boolean saveProductFamilyEnv(ProductFamilyEnvDTO dto) {
        ProductFamilyEnvDomain domain = convertProductFamilyEnvDTOToDomain(dto);
        ProductFamilyEnvDomain savedDomain = productFamilyEnvRepository.save(domain);
        if (savedDomain != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveProductCapabilityConfig(ProductCapabilityConfigDTO dto) {
        ProductCapabilityConfigDomain domain = convertProductCapabilityConfigDTOToDomain(dto);
        ProductCapabilityConfigDomain savedDomain = productCapabilityConfigRepository.save(domain);
        if (savedDomain != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<TenantDTO> getAllTenants() {
        List<TenantDTO> tenantDTOList = new ArrayList<>();
        List<TenantDomain> tenantDomainList = tenantRepository.findAll();
        if (tenantDomainList != null) {
            for (TenantDomain domain : tenantDomainList) {
                TenantDTO dto = convertTenantDomainToDTO(domain);
                tenantDTOList.add(dto);
            }

            return tenantDTOList;
        }
        return null;
    }

    @Override
    public List<ProductFamilyDTO> getProductFamilyByTenantId(String tenantId) {
        if (tenantId != null) {
            List<ProductFamilyDomain> domainList = productFamilyRepository.findByTenantId(tenantId);
            if (domainList != null) {
                List<ProductFamilyDTO> dtoList = new ArrayList<>();
                for (ProductFamilyDomain domain : domainList) {
                    dtoList.add(convertProductFamilyDomainToDTO(domain));
                }
                return dtoList;
            }
        }
        return null;
    }

    @Override
    public List<ProductCapabilityDTO> getAllCapabilities() {
        List<ProductCapabilityDTO> dtoList = new ArrayList<>();
        List<ProductCapabilityDomain> domainList = capabilityRepository.findAll();
        if (domainList != null) {
            for (ProductCapabilityDomain domain : domainList) {
                ProductCapabilityDTO dto = new ProductCapabilityDTO();
                dto.setCapabilityName(domain.getCapabilityName());
                dto.setCapabilityId(domain.getCapabilityId());
                dto.setRepoURL(domain.getRepoURL());
                dtoList.add(dto);
            }
            return dtoList;
        }

        return null;
    }

    @Override
    public List<ProductFamilyCapabilityDTO> getProductFamilyCapabilities(String capabilityName) {
        if (capabilityName != null) {
            List<ProductFamilyCapabilityDomain> domainList = productFamilyCapabilityRepository.findByCapabilityName(capabilityName);
            if (domainList != null) {
                List<ProductFamilyCapabilityDTO> dtoList = new ArrayList<>();
                for (ProductFamilyCapabilityDomain domain : domainList) {
                    ProductFamilyCapabilityDTO dto = new ProductFamilyCapabilityDTO();
                    dto.setId(domain.getId());
                    dto.setProductFamilyId(domain.getProductFamilyId());
                    dto.setCapabilityName(domain.getCapabilityName());
                    dto.setRepoURL(domain.getRepoURL());
                    dtoList.add(dto);
                }
                return dtoList;

            }

        }
        return null;
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
        domain.setTenantId(dto.getId());
        domain.setTenantName(dto.getTenantName());
        domain.setCreatedDate(dto.getCreatedDate());

        return domain;
    }

    private ProductFamilyDomain convertProductFamilyDTOToDomain(ProductFamilyDTO dto) {
        ProductFamilyDomain domain = new ProductFamilyDomain();
        domain.setProductFamilyId(dto.getId());
        domain.setProductFamilyName(dto.getProductFamilyName());
        domain.setTenantId(dto.getTenantId());
        domain.setSmartContractName(dto.getSmartContractName());
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
            dto.setId(domain.getTenantId());
            dto.setTenantName(domain.getTenantName());
            dto.setCreatedDate(domain.getCreatedDate());

            return dto;
        }

        return null;
    }

    private ProductFamilyDTO convertProductFamilyDomainToDTO(ProductFamilyDomain domain) {
        if (domain != null) {
            ProductFamilyDTO dto = new ProductFamilyDTO();
            dto.setId(domain.getProductFamilyId());
            dto.setProductFamilyName(domain.getProductFamilyName());
            dto.setTenantId(domain.getTenantId());
            dto.setSmartContractName(domain.getSmartContractName());
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

    private ProductCapabilityDTO convertProductCapabilityDomainToDTO(ProductCapabilityDomain domain) {
        if (domain != null) {
            ProductCapabilityDTO dto = new ProductCapabilityDTO();
            dto.setCapabilityId(domain.getCapabilityId());
            dto.setCapabilityName(domain.getCapabilityName());
            dto.setRepoURL(domain.getRepoURL());
            return dto;
        }
        return null;
    }

    private ProductFamilyCapabilityDomain convertProductFamilyCapabilityDTOToDomain(ProductFamilyCapabilityDTO dto) {
        if (dto != null) {
            ProductFamilyCapabilityDomain domain = new ProductFamilyCapabilityDomain();
            domain.setId(dto.getId());
            domain.setCapabilityName(dto.getCapabilityName());
            domain.setProductFamilyId(dto.getProductFamilyId());
            domain.setRepoURL(dto.getRepoURL());
            domain.setCreatedDate(dto.getCreatedDate());
            return domain;
        }
        return null;
    }

    private ProductFamilyEnvDomain convertProductFamilyEnvDTOToDomain(ProductFamilyEnvDTO dto) {
        if (dto != null) {
            ProductFamilyEnvDomain domain = new ProductFamilyEnvDomain();
            domain.setId(dto.getId());
            domain.setProductFamilyId(dto.getProductFamilyId());
            domain.setEnvName(dto.getEnvName());
            domain.setCreatedDate(dto.getCreatedDate());
            return domain;
        }
        return null;
    }

    private ProductCapabilityConfigDomain convertProductCapabilityConfigDTOToDomain(ProductCapabilityConfigDTO dto) {
        if (dto != null) {
            ProductCapabilityConfigDomain domain = new ProductCapabilityConfigDomain();
            domain.setId(dto.getId());
            domain.setCapabilityName(dto.getCapabilityName());
            domain.setProductFamilyId(dto.getProductFamilyId());
            domain.setExtensionType(dto.getExtensionType());
            domain.setExtURL(dto.getExtURL());
            domain.setCreatedDate(dto.getCreatedDate());

            return domain;

        }
        return null;
    }
}
