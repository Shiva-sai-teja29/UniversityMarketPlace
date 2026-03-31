package com.university.MarketPlace.email;

import com.university.MarketPlace.seller.dto.ShopCreationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    String[] to = new String[]{"paripellishiva@gmail.com","shivaparipelli29@gmail.com"};

    public EmailServiceImpl(JavaMailSender mailSender,
                            SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${spring.mail.username}")
    private String fromMailId;

    @Override
    public boolean sendNewShopRequest(ShopCreationRequest shopCreationRequest,String otp,String ownerName) throws MessagingException {

        Context context = new Context();
        context.setVariable("appName", "Shop Name: "+shopCreationRequest.getShopName()+"Shop owner name: "+ownerName);
        context.setVariable("name", otp);
        context.setVariable("message", "Shop Description: "+shopCreationRequest.getDescription()+" Request Note: "+shopCreationRequest.getRequestNote());
        //Add frontend url later to redirect to approvals
        context.setVariable("buttonLink", "https://University.netlify.app/");
        context.setVariable("buttonText", "Shop location: "+shopCreationRequest.getLocation());
        context.setVariable("expires", LocalDateTime.now().plusDays(2));

        String htmlContent = templateEngine.process("email-content", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setFrom(fromMailId);
        helper.setSubject("Finance Tracking App Password reset");
        helper.setText(htmlContent, true);

        try {
            mailSender.send(message);
            logger.info("Mail sent to: {}", Arrays.toString(to));
            return true;
        }catch (Exception e) {
            logger.error("Failed to send email: {}", e.getMessage());
            return false;
        }
    }
}
