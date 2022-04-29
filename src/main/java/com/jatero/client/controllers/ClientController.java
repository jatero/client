package com.jatero.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ClientController {
	
	@Autowired
	private EurekaClient client;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	//Prepara la llamada al REST service
	@RequestMapping("/")
	public String callService() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		System.out.println(client.getApplicationInfoManager().toString());
		System.out.println(client.getEurekaClientConfig().toString());
		InstanceInfo instanceInfo = client.getNextServerFromEureka("service", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
		return response.getBody();
	}
}
