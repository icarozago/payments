package com.icaro.payments.configuration;

import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	public ResourceBundle resourceBundle() {
		return ResourceBundle.getBundle("resource");
	}
	
}
