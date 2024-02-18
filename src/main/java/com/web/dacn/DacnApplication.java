package com.web.dacn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DacnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DacnApplication.class, "--environment_id=ymdJiEtrQOPji2TRCScZ",
				"--access_key=NkrUSak597PhpSM14i4q2I6kOdY37x45OPoVYB2i8iISdgIRxEJ9PjyTjp1H");
	}
}
