package com.csee.swplus.mileage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//@MapperScan("com.csee.swplus.mileage.scholarship.mapper")
public class MileageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MileageApplication.class, args);
//		SpringApplication app = new SpringApplicationBuilder(MileageApplication.class).properties(
//						"spring.config.location="
//								+"classpath:/application.yml")
//				.build();
//		app.run(args);
	}

}
