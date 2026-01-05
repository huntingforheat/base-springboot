package com.example.demo;

import com.example.demo.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		cc();
	}

	@Autowired
	private JWTUtil jwtUtil;

	private void cc() {

		Map<String, Object> claims = Map.of(
				"userId", "siu"
		);

		String ss = jwtUtil.createJwt(claims, 1);

		System.out.println("====================: " + ss);
	}

}
