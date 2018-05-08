package com.emag.controller;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailSender extends Thread {
	
	private final String senderEmailID = "emag.ittalents@gmail.com";
	private final String senderPassword = "emagittalents";
	private final String emailSMTPserver = "smtp.gmail.com";
	private final String emailServerPort = "465";
	private String receiverEmailID = null;
	private String emailSubject=null;
	private String emailBody=null;
	
	public MailSender(String receiverEmailID, String emailSubject, String emailBody) {
		this.receiverEmailID= receiverEmailID;
		this.emailSubject=emailSubject;
		this.emailBody=emailBody;	
	}
	
	@Override
	public void run() {
		Properties props = new Properties();
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.imap.ssl.trust", "*");
			props.put("mail.imap.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}		
		props.put("mail.smtp.user",senderEmailID);
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", emailServerPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		

		SecurityManager security = System.getSecurityManager();
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(senderEmailID, senderPassword);
					}
				});
		
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setText(emailBody);
				msg.setSubject(emailSubject);
				msg.setFrom(new InternetAddress(senderEmailID));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
		        Transport.send(msg);
		        System.out.println("---Done---");
				
				System.out.println("Message send Successfully:)");
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	
	
//	public class SMTPAuthenticator extends javax.mail.Authenticator {
//		public PasswordAuthentication getPasswordAuthentication() {
//
//			return new PasswordAuthentication(senderEmailID, senderPassword);
//		}
//	}
	
}