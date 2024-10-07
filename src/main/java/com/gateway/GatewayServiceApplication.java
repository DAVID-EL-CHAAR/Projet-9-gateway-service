package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.session.data.r2dbc.config.annotation.web.server.EnableR2dbcHttpSession;


@SpringBootApplication
//@EnableR2dbcHttpSession
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
