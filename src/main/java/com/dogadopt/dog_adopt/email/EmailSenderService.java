package com.dogadopt.dog_adopt.email;

public interface EmailSenderService {

    void send(String to, String email, String subject);
}
