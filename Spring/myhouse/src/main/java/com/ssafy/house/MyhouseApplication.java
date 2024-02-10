package com.ssafy.house;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
public class MyhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyhouseApplication.class, args);
	}

}
