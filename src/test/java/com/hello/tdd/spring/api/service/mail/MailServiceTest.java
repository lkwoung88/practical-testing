package com.hello.tdd.spring.api.service.mail;

import com.hello.tdd.spring.client.mail.MailSendClient;
import com.hello.tdd.spring.domain.history.mail.MailSendHistory;
import com.hello.tdd.spring.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @Mock
    private MailSendClient mailSendClient;
//    @Spy
//    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail(){

        // given
//        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
//                .thenReturn(true);

        BDDMockito.given(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .willReturn(true);

//        Mockito.doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(any(String.class), any(String.class), any(String.class), any(String.class));

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}