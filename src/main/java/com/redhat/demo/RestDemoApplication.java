package com.redhat.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.redhat.demo")
public class RestDemoApplication {
	

	    
	    

    public static void main(String[] args) {
    	
        SpringApplication.run(RestDemoApplication.class, args);   
    }
    
    
//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.
//				build();
//	}


	
}
