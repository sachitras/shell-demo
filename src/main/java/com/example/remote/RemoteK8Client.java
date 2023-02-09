package com.example.remote;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Component
public class RemoteK8Client {

    public void sendK8Command() {
        try {
            KubernetesClient kubernetesClient = new KubernetesClientBuilder()
                    .withConfig(new ConfigBuilder()
                            .withMasterUrl("")
                            .withOauthToken("")
                            .withNamespace("")
                            .withCaCertFile("")
                            .withClientCertFile("")
                            .withClientKeyFile("")
                            .withClientKeyAlgo("")
                            .build())
                    .build();



        } catch (Exception e){}
    }

    public boolean isNamespaceAvailable(String namespaceName) {

        if (namespaceName != null) {
            namespaceName = formatK8String(namespaceName);

            KubernetesClient client = new DefaultKubernetesClient();
            NamespaceList namespaceList = client.namespaces().list();
            List<Namespace> namespaces = namespaceList.getItems();
            if (namespaces != null) {
                for (Namespace namespace : namespaces) {
                    if (namespaceName.equals(namespace.getMetadata().getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void createNamespace(String namespaceName) {
        if (namespaceName != null) {
            namespaceName = formatK8String(namespaceName);

            KubernetesClient client = new DefaultKubernetesClient();
            Namespace namespace = client.namespaces().create(
                    new NamespaceBuilder().withNewMetadata().
                            withName(namespaceName.toLowerCase()).endMetadata().build());
        }
    }

    public boolean isServiceAlreadyAvailable(String namespace, String serviceName) {
        if (namespace != null && serviceName != null) {
            KubernetesClient client = new DefaultKubernetesClient();
            ServiceList serviceList = client.services().inNamespace(formatK8String(namespace)).list();
            List<Service> services = serviceList.getItems();
            for (Service service : services) {
                if (formatK8String(serviceName).equals(service.getMetadata().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Corresponding yaml file structure: an example
     *
     * apiVersion: v1
     * kind: Service
     * metadata:
     *      name: accounts
     * spec:
     *      type: LoadBalancer
     *      ports:
     *          - protocol: TCP
     *            port: 81
     *            targetPort: 8081
     *
     *
     * @param nameSpaceName
     * @param serviceName
     */
    public void createService(String nameSpaceName, String serviceName) {
        if (nameSpaceName != null && serviceName != null) {
            KubernetesClient client = new DefaultKubernetesClient();
            Service service = client.services().inNamespace(formatK8String(nameSpaceName)).create(
                    new ServiceBuilder().withNewMetadata()
                            .withName(formatK8String(serviceName)).endMetadata()
                            .withNewSpec().withType("LoadBalancer").addNewPort().withProtocol("TCP").withPort(81)
                            .withNewTargetPort(8081).endPort().endSpec().build());
            System.out.println("The new service was created > " + serviceName);
        }

    }

    public void runDeployment(String capabilityName, String namespaceName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File resource = new File(classLoader.getResource("classpath:kube/"+capabilityName).getFile());
            if (resource.exists()) {
                if (resource.isDirectory()) {
                    File [] files = resource.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            String fileName = file.getName();
                            if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
                                InputStream in = new FileInputStream(file);
                                KubernetesClient client = new DefaultKubernetesClient();
//                                client.pods().inNamespace(namespaceName).load(in).create();
                                client.load(in).inNamespace(namespaceName).create();
//                                Service service = client.services().inNamespace(namespaceName).load(file).create();
                            }
                        }
                    }
                } else {
                    System.out.println("Not a directory.");
                }
            } else {
                System.out.println("Deployment file not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }



    }

    private String formatK8String(String value) {
        if (value != null) {
            String finalStr = "";
            finalStr = value.toLowerCase();
            if (finalStr.contains("_")) {
                finalStr = finalStr.replace('_', '-');
            }

            return finalStr;
        }
        return null;
    }
}
