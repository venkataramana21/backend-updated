package com.example.message_service.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
   
    private String accountSid="ACeb25f13bc86986d5def7f248c6aefd12";
    private String authToken = "86d75118f24074c3b6f297979f407ad1";
    private String fromPhoneNumber = "+13156464086";

    public MessageService() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), message).create();
    }

}
