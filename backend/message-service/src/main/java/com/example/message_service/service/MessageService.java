package com.example.message_service.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
   
    private String accountSid="";
    private String authToken = "";
    private String fromPhoneNumber = "";

    public MessageService() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), message).create();
    }

}
