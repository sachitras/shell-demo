package com.example.remote;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;

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
}
