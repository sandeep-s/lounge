package com.afkl.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.afkl.utils.Constants;
import com.afkl.utils.Utils;

@Service
public class AirportService {

	private OAuthTokenService oAuthTokenService;

	@Autowired
	public void setoAuthTokenService(OAuthTokenService oAuthTokenService) {
		this.oAuthTokenService = oAuthTokenService;
	}

	Properties config = Utils.getAllProperties(Constants.CONFIG_FILE_PATH);

	public String getAirportDetails() {
		final String endpoint = config.getProperty(Constants.AIRPORTS_ENDPOINT);
		String response = postRestTemplate_Call(endpoint);
		return response;
	}

	public String getSpecificAiportDetail(String code) {
		final String endpoint = config.getProperty(Constants.AIRPORTS_ENDPOINT) + code;
		String response = postRestTemplate_Call(endpoint);
		return response;
	}

	public String getAFare(String originCode, String destinationCode) {
		final String endpoint = config.getProperty(Constants.FARE_ENDPOINT) + originCode
				+ config.getProperty(Constants.SLASH) + destinationCode;
		String response = postRestTemplate_Call(endpoint);
		return response;
	}

	private String postRestTemplate_Call(String uri) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(config.getProperty(Constants.AUTHORIZATION), config.getProperty(Constants.BEARER) + " " + oAuthTokenService.getOAuth2Token());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
}