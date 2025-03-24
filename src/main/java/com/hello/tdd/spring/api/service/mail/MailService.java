package com.hello.tdd.spring.api.service.mail;

import com.hello.tdd.spring.client.mail.MailSendClient;
import com.hello.tdd.spring.domain.history.mail.MailSendHistory;
import com.hello.tdd.spring.domain.history.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .subject(subject)
                    .content(content)
                    .build());

            mailSendClient.a();
            mailSendClient.b();
            mailSendClient.c();

            return true;
        }

        return false;
    }
}
