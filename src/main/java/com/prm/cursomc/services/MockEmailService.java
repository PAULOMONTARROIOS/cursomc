package com.prm.cursomc.services;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		LOG.info("Simluando envio de e-mail...");
		LOG.info(msg.toString());
		LOG.info("Email enviado !");
		
	}

}
