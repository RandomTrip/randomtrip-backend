package com.ssafy.vue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

//@SpringBootTest(
//		properties = {
//			"userid=ssafy",
//			"userpwd=1234"
//		}
//	)
@SpringBootTest(
		properties = {
			"spring.config.location=classpath:application.properties"
		}
	)
@Slf4j
class VueApiApplicationTests {
	
	@Value("${userid}")
	private String userId;
	@Value("${userpwd}")
	private String userPwd;

	@Test
	@DisplayName("customer property test")
	void contextLoads() {
		log.info("############ properties test ############");
		log.info("userId : {}", userId);
		log.info("userPwd : {}", userPwd);
	}

}
