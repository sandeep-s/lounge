package com.afkl.service;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.afkl.utils.Constants;
import com.afkl.utils.Utils;
import com.google.common.base.Splitter;

@Service
public class OAuthTokenService {

	Properties config = Utils.getAllProperties(Constants.CONFIG_FILE_PATH);

	public String getOAuth2Token() {
		String tokenUri = config.getProperty(Constants.TOKEN_URI);
		String responseBody = getAccessToken(tokenUri);
		String oAuthToken = extractAccessToken(responseBody);
		return oAuthToken;
	}

	private String getAccessToken(String tokenUri) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(config.getProperty(Constants.AUTHORIZATION), config.getProperty(Constants.BASIC) + " " + Base64.encodeBase64String((config.getProperty(Constants.CLIENT_ID)
				+ config.getProperty(Constants.SEMI_COLON) + config.getProperty(Constants.CLIENT_KEY)).getBytes()));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add(config.getProperty(Constants.GRANT_TYPE), config.getProperty(Constants.CLIENT_CREDENTIALS));
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, entity, String.class);
		return response.getBody();
	}

	private String extractAccessToken(String responseBody) {
		Map<String, String> values = Splitter.on(config.getProperty(Constants.COMMA))
				.withKeyValueSeparator(config.getProperty(Constants.SEMI_COLON)).split(responseBody);
		if (values.containsKey(config.getProperty(Constants.ACCESS_TOKEN))) {
            String accessToken = ((String) values.get(config.getProperty(Constants.ACCESS_TOKEN))).replace("\"","");
            return accessToken;
        }
		else
			return null;
	}
}
