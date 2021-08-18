package com.web;

import com.feign.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@EnableFeignClients
@Import(value={Config.class})
@SpringBootApplication
public class UtWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtWebServiceApplication.class, args);
	}

}
