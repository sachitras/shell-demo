package com.example.remote;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RemoteWebClient {

	WebClient webClient = WebClient.create("https://core-api-demo.thunderball.tmachine.io");
	
	public CreateProductFamilyResponse createAccount(ProductFamilyPayLoad request) {
		Mono<CreateProductFamilyResponse> response = this.webClient.post()
															.uri("/v1/product-versions")
															.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
															.body(Mono.just(request), ProductFamilyPayLoad.class)
															.retrieve()
															.bodyToMono(CreateProductFamilyResponse.class);

		return response.block();
	}


}
