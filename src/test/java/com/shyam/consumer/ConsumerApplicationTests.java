package com.shyam.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

class ConsumerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void test1(){
		Instant now = Instant.now();
		System.out.println(new Date().getTime());
	}

}
