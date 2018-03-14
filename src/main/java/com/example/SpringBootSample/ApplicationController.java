package com.example.SpringBootSample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-sample/")
public class ApplicationController {
	
	@RequestMapping("/hi")
	private String getHi() {
		return "Hi Sri - App User is working on Openshift";
	}
}
