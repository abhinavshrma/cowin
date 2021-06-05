package com.abhinav.cowin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.abhinav.cowin.mail.MailHandler;

@SpringBootApplication
@EnableScheduling
public class CowinApplication {
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(CowinApplication.class, args);

	}
}
