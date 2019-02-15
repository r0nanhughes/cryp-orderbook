package com.cryppro.cryporderbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CrypOrderbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrypOrderbookApplication.class, args);
	}
	
	@Bean
	public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}

}

