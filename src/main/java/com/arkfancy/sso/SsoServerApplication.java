package com.arkfancy.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.arkfancy.sso.support.annotation.EnableSSO;

@SpringBootApplication
@EnableSSO
public class SsoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class, args);
	}

}
