package com.abhinav.cowin.mail;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailHandler {
	
	@Autowired
    private JavaMailSender javaMailSender;
    
    public void sendEmail(List<String> list, String vaccine, int districtId) {
    	String text = "";
    	String city = "";
    	if(districtId == 496) {
    		city = "Mohali";
    	}else if(districtId == 108) {
    		city = "Chandigarh";
    	}
    	for (String string : list) {
    		text = text.concat(string);
		}
    	text = "Snapshot taken at:"+new Date()+"\n\n"+text;
    	try {
        SimpleMailMessage msg = new SimpleMailMessage();
        //msg.setTo("to_1@gmail.com", "to_2@gmail.com", "to_3@yahoo.com");
        msg.setTo("theabhinavsharma@hotmail.com");
        msg.setSubject(city+" "+vaccine+" Snapshot");
        msg.setText(text);

        javaMailSender.send(msg);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("Email Sent Successfully!!");
    }

}
