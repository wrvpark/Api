package com.wrvpark.apiserver.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

/**
 * @author Vahid Haghighat
 */
public class EmailUtil {
    public static int send(String fromEmail, String toEmail, String subject, String content) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content contents = new Content("text/html", content);
        Mail mail = new Mail(from, subject, to, contents);

        SendGrid sendGrid = new SendGrid(System.getenv("SG_API"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            return response.getStatusCode();
        } catch (IOException e) {
            return 500;
        }
    }
}
