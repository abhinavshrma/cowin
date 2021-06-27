package com.abhinav.cowin.mail;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.abhinav.cowin.utils.Utils;

@Component
public class MailHandler {

	@Autowired
	private JavaMailSender javaMailSender;

	private String to_email = "cowin.email.to";

	public void sendEmail(List<String> list, String vaccine, int districtId) {
		String text = "";
		for (String string : list) {
			text = text.concat(string);
		}
		text = "Snapshot taken at:" + new Date() + "\n\n" + text;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(Utils.propertiesUtility(to_email));
			msg.setSubject(Utils.getCity(districtId) + " " + vaccine + " Snapshot");
			msg.setText(text);

			javaMailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Email Sent Successfully!!");
	}

	public void sendMailWithAttachment(String subject, String body, File fileToAttach) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(Utils.propertiesUtility(to_email));
			helper.setSubject(subject);
			helper.setText(body);
			helper.addAttachment(fileToAttach.getName(), fileToAttach);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaMailSender.send(message);
		System.out.println("Backup Email Sent Successfully!!");
	}

}
