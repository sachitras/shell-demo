package com.example.remote;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RemoteWebClient {

	WebClient webClient = WebClient.create("http://localhost:8582");
	
	public CreateProductFamilyResponse createAccount(CreateProductFamilyRequest request) {
//		Mono<CreateProductFamilyResponse> response = this.webClient.post()
//															.uri("/api/ext/account")
//															.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//															.body(Mono.just(request), CreateProductFamilyRequest.class)
//															.retrieve()
//															.bodyToMono(CreateProductFamilyResponse.class);
		
		CreateProductFamilyResponse response = new CreateProductFamilyResponse();
		response.setStatusCode("SUCCESS");
		
		return response;
	}


}
